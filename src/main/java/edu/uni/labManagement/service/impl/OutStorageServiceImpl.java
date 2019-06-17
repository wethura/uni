package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.OutStorage;
import edu.uni.labManagement.bean.OutStorageDetail;
import edu.uni.labManagement.bean.OutStorageDetailExample;
import edu.uni.labManagement.bean.OutStorageExample;
import edu.uni.labManagement.config.LabManagementConfig;
import edu.uni.labManagement.mapper.OutStorageDetailMapper;
import edu.uni.labManagement.mapper.OutStorageMapper;
import edu.uni.labManagement.service.OutStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OutStorageServiceImpl implements OutStorageService {
    @Resource
    private OutStorageMapper outStorageMapper;

    @Resource
    private OutStorageDetailMapper outStorageDetailMapper;
    @Autowired
    private AuthService authService;

    @Autowired
    private LabManagementConfig globalConfig;

    private Integer getNextBatch() {
        Integer batch = outStorageMapper.selectMaxBatch();
        return batch==null ? 1 : batch+1;
    }

    /**
     * 新增设备出库记录
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean insert(Map<String,Object> json) {
        OutStorage outStorage = new OutStorage();
        outStorage.setBatch(getNextBatch());
        outStorage.setDescription((String) json.get("Description"));
        outStorage.setSource((String) json.get("Source"));
        outStorage.setDepartmentId(Long.parseLong((String)json.get("DepartmentId")));
        outStorage.setUniversityId(((User)authService.getUser()).getUniversityId());
        outStorage.setDatetime(new Date());
        outStorage.setByWho(((User)authService.getUser()).getId());
        outStorage.setDeleted(false);
        if(outStorageMapper.insert(outStorage) <= 0) return false;

        OutStorageDetail outStorageDetail = new OutStorageDetail();
        outStorageDetail.setStorageId(outStorage.getId());
        outStorageDetail.setUniversityId(outStorage.getUniversityId());
        outStorageDetail.setDatetime(outStorage.getDatetime());
        outStorageDetail.setByWho(outStorage.getByWho());
        outStorageDetail.setDeleted(outStorage.getDeleted());
        for(String id : (List<String>)json.get("Arr")) {
            outStorageDetail.setDeviceId(Long.parseLong(id));
            if(outStorageDetailMapper.insert(outStorageDetail) <= 0) return false;
        }
        return true;
    }

    /**
     * 删除设备出库记录
     * @param id 主键
     * @return boolean
     */
    @Override
    public boolean delete(Long id){
        OutStorage outStorage = new OutStorage();
        outStorage.setId(id);
        outStorage.setDeleted(true);
        return outStorageMapper.updateByPrimaryKeySelective(outStorage) > 0;
    }

    /**
     * 修改设备出库记录
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean update(Map<String,Object> json){
        OutStorage outStorage = new OutStorage();
        outStorage.setId(Long.parseLong((String)json.get("Id")));
        outStorage.setDescription((String) json.get("Description"));
        outStorage.setSource((String) json.get("Source"));
        outStorage.setDepartmentId(Long.parseLong((String)json.get("DepartmentId")));
        return outStorageMapper.updateByPrimaryKeySelective(outStorage) > 0;
    }

    /**
     * 查询单条设备出库记录
     * @param id 主键
     * @return Map<String,Object>
     */
    @Override
    public Map<String,Object> select(Long id) {
        OutStorageExample example = new OutStorageExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<OutStorage> list = outStorageMapper.selectByExample(example);
        if(list.isEmpty()) return null;
        OutStorage outStorage = list.get(0);

        Map<String,Object> res = new HashMap<>();
        res.put("id", outStorage.getId());
        res.put("batch", outStorage.getBatch());
        res.put("description", outStorage.getDescription());
        res.put("source", outStorage.getSource());
        res.put("departmentId", outStorage.getDepartmentId());
        res.put("datetime", new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(outStorage.getDatetime()));

        OutStorageDetailExample example2 = new OutStorageDetailExample();
        example2.or().andStorageIdEqualTo(id).andDeletedEqualTo(false);
        List<OutStorageDetail> list2 = outStorageDetailMapper.selectByExample(example2);
        List<Long> arr = new LinkedList<>();
        for(OutStorageDetail outStorageDetail : list2) {
            arr.add(outStorageDetail.getDeviceId());
        }
        res.put("arr", arr);
        return res;
    }

    /**
     * 分页查询所有设备出库记录
     * @param pageNum 页码
     * @return PageInfo<Map<String,Object>>
     */
    @Override
    public PageInfo<Map<String,Object>> selectPage(int pageNum){
        PageHelper.startPage(pageNum, globalConfig.getPageSize()); //开启分页查询，仅第一次查询时生效

        OutStorageExample example = new OutStorageExample();
        example.or().andDeletedEqualTo(false);
        List<OutStorage> res = outStorageMapper.selectByExample(example);

        List<Map<String,Object>> list = new LinkedList<>();
        for(OutStorage outStorage : res) {
            list.add(select(outStorage.getId()));
        }

        if(!list.isEmpty()) return new PageInfo<>(list);
        return null;
    }
}
