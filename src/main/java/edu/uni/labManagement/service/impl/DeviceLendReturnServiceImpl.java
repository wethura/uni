package edu.uni.labManagement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.User;
import edu.uni.labManagement.bean.*;
import edu.uni.labManagement.mapper.*;
import edu.uni.labManagement.service.DeviceLendReturnService;
import edu.uni.utils.JsonUtils;
import io.swagger.annotations.Example;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DeviceLendReturnServiceImpl implements DeviceLendReturnService {
    @Resource
    private DeviceLendReturnMapper deviceLendReturnMapper;

    @Resource
    private DeviceLendReturnDetailMapper deviceLendReturnDetailMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private PFieldMapper fieldMapper;

    @Resource
    private DeviceCategoryMapper deviceCategoryMapper;

    @Resource
    private PSchoolAreaMapper schoolAreaMapper;

    @Resource
    private PSchoolMapper schoolMapper;

    @Autowired
    private AuthService authService;
    /**
     * 借出设备
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean insert(Map<String,Object> json) throws Exception {
        DeviceLendReturn deviceLendReturn = new DeviceLendReturn();
        deviceLendReturn.setUserId(Long.parseLong((String)json.get("userId")));
        List<Integer> list = (List<Integer>)json.get("fieldId");
        Long fieldId = (long)list.get(list.size()-1);
        deviceLendReturn.setFieldId(fieldId);

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        deviceLendReturn.setPlanLendTime(sdf.parse((String)json.get("planLendTime")));
        deviceLendReturn.setPlanReturnTime(sdf.parse((String)json.get("planReturnTime")));

        deviceLendReturn.setApproval(0);
        deviceLendReturn.setReturned(false);
//        添加university
        deviceLendReturn.setUniversityId(((edu.uni.auth.bean.User)authService.getUser()).getUniversityId());
        deviceLendReturn.setDatetime(new Date());
//        添加byWho
        deviceLendReturn.setByWho(((edu.uni.auth.bean.User)authService.getUser()).getId());
        deviceLendReturn.setDeleted(false);
        if(deviceLendReturnMapper.insert(deviceLendReturn) <= 0) return false;

        DeviceLendReturnDetail deviceLendReturnDetail = new DeviceLendReturnDetail();
        deviceLendReturnDetail.setLendReturnId(deviceLendReturn.getId());
        deviceLendReturnDetail.setUniversityId(deviceLendReturn.getUniversityId());
        deviceLendReturnDetail.setDatetime(deviceLendReturn.getDatetime());
        deviceLendReturnDetail.setByWho(deviceLendReturn.getByWho());
        deviceLendReturnDetail.setDeleted(deviceLendReturn.getDeleted());

        List<Integer> list1 = (List<Integer>)json.get("categoryId");
        Long categoryId = (long)list1.get(list1.size()-1);
        String num = (String)json.get("num");
        deviceLendReturnDetail.setCategoryId(categoryId);
        deviceLendReturnDetail.setNum(Integer.parseInt(num));
        if(deviceLendReturnDetailMapper.insert(deviceLendReturnDetail) <= 0) return false;
        return true;
    }

    /**
     * 审批借出申请
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean updateApproval(Map<String,Object> json) {
        DeviceLendReturn deviceLendReturn = new DeviceLendReturn();
        deviceLendReturn.setId(Long.parseLong((String) json.get("id")));
        deviceLendReturn.setApproval(Integer.parseInt((String) json.get("approval")));
        return deviceLendReturnMapper.updateByPrimaryKeySelective(deviceLendReturn) > 0;
    }

    /**
     * 归还设备
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean updateReturned(Map<String,Object> json) {
        DeviceLendReturn deviceLendReturn = new DeviceLendReturn();
        deviceLendReturn.setId(Long.parseLong((String) json.get("id")));
        deviceLendReturn.setReturned(true);
        return deviceLendReturnMapper.updateByPrimaryKeySelective(deviceLendReturn) > 0;
    }

    /**
     * 查询单条设备借出归还记录
     * @param id 主键
     * @return Map<String,Object>
     */
    @Override
    public Map<String,Object> select(Long id) {
        DeviceLendReturnExample example = new DeviceLendReturnExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<DeviceLendReturn> list = deviceLendReturnMapper.selectByExample(example);
        if(list.isEmpty()) return null;
        DeviceLendReturn deviceLendReturn = list.get(0);

        Map<String,Object> res = new HashMap<>();
        res.put("id", deviceLendReturn.getId());

        User user = userMapper.selectByPrimaryKey(deviceLendReturn.getUserId());
        res.put("username", user == null ? null : user.getUserName());

        PField field = fieldMapper.selectByPrimaryKey(deviceLendReturn.getFieldId());
        PSchoolArea area = schoolAreaMapper.selectByPrimaryKey(field.getAreaId());
        PSchool school = schoolMapper.selectByPrimaryKey(area.getSchoolId());
        res.put("fieldName", school.getName() + "/" + area.getName() + "/" + field.getName());

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        res.put("planLendTime", sdf.format(deviceLendReturn.getPlanLendTime()));
        res.put("planReturnTime", sdf.format(deviceLendReturn.getPlanReturnTime()));

        DeviceLendReturnDetailExample example1 = new DeviceLendReturnDetailExample();
        example1.or().andLendReturnIdEqualTo(deviceLendReturn.getId());
        List<DeviceLendReturnDetail> list1 = deviceLendReturnDetailMapper.selectByExample(example1);
        DeviceLendReturnDetail detail = list1.get(0);
        DeviceCategory deviceCategory = deviceCategoryMapper.selectByPrimaryKey(detail.getCategoryId());
        String categoryNames = deviceCategory.getName();
        while (deviceCategory.getPid() != null) {
            deviceCategory = deviceCategoryMapper.selectByPrimaryKey(deviceCategory.getPid());
            categoryNames = deviceCategory.getName() + "/" + categoryNames;
        }
        res.put("categoryName", categoryNames);

        res.put("num", detail.getNum());
        res.put("datetime", sdf.format(deviceLendReturn.getDatetime()));
        return res;
    }

    /**
     * 根据Example分页查询
     * @param example Example类
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return PageInfo<Map<String,Object>>
     */
    public PageInfo<Map<String,Object>> selectPageByExample(DeviceLendReturnExample example, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize); //开启分页查询，仅第一次查询时生效
        List<DeviceLendReturn> res = deviceLendReturnMapper.selectByExample(example);

        List<Map<String,Object>> list = new LinkedList<>();
        for(DeviceLendReturn deviceLendReturn : res) {
            list.add(select(deviceLendReturn.getId()));
        }

        PageInfo<DeviceLendReturn> temp = new PageInfo<>(res);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
        temp.setList(null);
        BeanUtils.copyProperties(temp, pageInfo);
        pageInfo.setList(list);
        return pageInfo;
    }

    /**
     * 分页查询可审批的借出申请
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return PageInfo<Map<String,Object>>
     */
    @Override
    public PageInfo<Map<String,Object>> selectPageApproval(int pageNum, int pageSize) {
        DeviceLendReturnExample example = new DeviceLendReturnExample();
        example.or().andApprovalEqualTo(0).andDeletedEqualTo(false);
        return selectPageByExample(example, pageNum, pageSize);
    }

    /**
     * 分页查询可归还的设备
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @return PageInfo<Map<String,Object>>
     */
    @Override
    public PageInfo<Map<String,Object>> selectPageReturned(int pageNum, int pageSize) {
        DeviceLendReturnExample example = new DeviceLendReturnExample();
        example.or().andApprovalEqualTo(1).andReturnedEqualTo(false).andDeletedEqualTo(false);
        return selectPageByExample(example, pageNum, pageSize);
    }

    /**
     * 分页高级筛选可归还的设备
     * @param json 请求参数
     * @return PageInfo<Map<String,Object>>
     */
    @Override
    public PageInfo<Map<String,Object>> filterReturned(Map<String,Object> json) {
        String username = (String) json.get("username");
        List<Long> fieldIds = Tool.listIntToLong((List<Integer>) json.get("fieldName"));
        List<Long> categoryIds = Tool.listIntToLong((List<Integer>) json.get("categoryName"));
        Integer pageNum = (Integer) json.get("pageNum");
        Integer pageSize = (Integer) json.get("pageSize");
        if(pageNum == null) pageNum = 1;
        if(pageSize == null) pageSize = 1000;

        DeviceLendReturnExample example = new DeviceLendReturnExample();
        DeviceLendReturnExample.Criteria criteria = example.createCriteria();
        if(username != null) {
            List<Long> userIds = Tool.getIdsByCriteria(new UserExample().or().
                    andUserNameLike("%" + username + "%"), "getId");
            criteria.andUserIdIn(userIds);
        }
        if(fieldIds != null) {
            while (fieldIds.size() > 1) fieldIds.remove(0);
            if(fieldIds.isEmpty()) fieldIds.add(-9L);
            criteria.andFieldIdIn(fieldIds);
        }
        if(categoryIds != null) {
            while (categoryIds.size() > 1) categoryIds.remove(0);
            if(categoryIds.isEmpty()) categoryIds.add(-9L);
            List<Long> ids = Tool.getIdsByCriteria(new DeviceLendReturnDetailExample().or().
                    andCategoryIdIn(categoryIds), "getLendReturnId");
            criteria.andIdIn(ids);
        }
        criteria.andApprovalEqualTo(1).andReturnedEqualTo(false)
                .andDeletedEqualTo(false);
        return selectPageByExample(example, pageNum, pageSize);
    }
}
