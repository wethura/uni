package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.DeviceLend;
import edu.uni.labManagement.bean.DeviceLendDetail;
import edu.uni.labManagement.bean.DeviceLendDetailExample;
import edu.uni.labManagement.bean.DeviceLendExample;
import edu.uni.labManagement.config.LabManagementConfig;
import edu.uni.labManagement.mapper.DeviceLendDetailMapper;
import edu.uni.labManagement.mapper.DeviceLendMapper;
import edu.uni.labManagement.service.DeviceLendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DeviceLendServiceImpl implements DeviceLendService {
    @Resource
    private DeviceLendMapper deviceLendMapper;

    @Resource
    private DeviceLendDetailMapper deviceLendDetailMapper;

    @Autowired
    private LabManagementConfig globalConfig;
    @Autowired
    private AuthService authService;
    /**
     * 新增设备借出记录
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean insert(Map<String,Object> json) {
        DeviceLend deviceLend = new DeviceLend();
        deviceLend.setUserId(Long.parseLong((String)json.get("userId")));
        deviceLend.setReason((String) json.get("reason"));
        deviceLend.setUniversityId(((User)authService.getUser()).getUniversityId());
        deviceLend.setDatetime(new Date());
        deviceLend.setByWho(((User)authService.getUser()).getId());
        deviceLend.setDeleted(false);
        if(deviceLendMapper.insert(deviceLend) <= 0) return false;

        DeviceLendDetail deviceLendDetail = new DeviceLendDetail();
        deviceLendDetail.setDeviceLendId(deviceLend.getId());
        deviceLendDetail.setUniversityId(deviceLend.getUniversityId());
        deviceLendDetail.setDatetime(deviceLend.getDatetime());
        deviceLendDetail.setByWho(deviceLend.getByWho());
        deviceLendDetail.setDeleted(deviceLend.getDeleted());
        for(String id : (List<String>)json.get("arr")) {
            deviceLendDetail.setDeviceId(Long.parseLong(id));
            if(deviceLendDetailMapper.insert(deviceLendDetail) <= 0) return false;
        }
        return true;
    }

    /**
     * 删除设备借出记录
     * @param id 主键
     * @return boolean
     */
    @Override
    public boolean delete(Long id){
        DeviceLend deviceLend = new DeviceLend();
        deviceLend.setId(id);
        deviceLend.setDeleted(true);
        return deviceLendMapper.updateByPrimaryKeySelective(deviceLend) > 0;
    }

    /**
     * 修改设备借出记录
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean update(Map<String,Object> json) throws Exception {
        DeviceLend deviceLend = new DeviceLend();
        deviceLend.setId(Long.parseLong((String)json.get("id")));
        if(json.get("userId") != null) deviceLend.setUserId(Long.parseLong((String)json.get("userId")));
        if(json.get("reason") != null) deviceLend.setReason((String) json.get("reason"));
        if(json.get("returnTime") != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
            deviceLend.setReturnTime(sdf.parse((String)json.get("returnTime")));
        }
//        deviceLend.setByWho(((User)authService.getUser()).getId());
//        deviceLend.setUniversityId(((User)authService.getUser()).getUniversityId());
        return deviceLendMapper.updateByPrimaryKeySelective(deviceLend) > 0;
    }

    /**
     * 查询单条设备借出记录
     * @param id 主键
     * @return Map<String,Object>
     */
    @Override
    public Map<String,Object> select(Long id){
        DeviceLendExample example = new DeviceLendExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<DeviceLend> list = deviceLendMapper.selectByExample(example);
        if(list.isEmpty()) return null;
        DeviceLend deviceLend = list.get(0);

        Map<String,Object> res = new HashMap<>();
        res.put("id", deviceLend.getId());
        res.put("userId", deviceLend.getUserId());
        res.put("reason", deviceLend.getReason());
        if(deviceLend.getReturnTime() == null) res.put("returnTime", null);
        else res.put("returnTime", new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(deviceLend.getReturnTime()));
        res.put("datetime", new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(deviceLend.getDatetime()));

        DeviceLendDetailExample example2 = new DeviceLendDetailExample();
        example2.or().andDeviceLendIdEqualTo(id).andDeletedEqualTo(false);
        List<DeviceLendDetail> list2 = deviceLendDetailMapper.selectByExample(example2);
        List<Long> arr = new LinkedList<>();
        for(DeviceLendDetail deviceLendDetail : list2) {
            arr.add(deviceLendDetail.getDeviceId());
        }
        res.put("arr", arr);
        return res;
    }

    /**
     * 分页查询所有设备借出记录
     * @param pageNum 页码
     * @return PageInfo<Map<String,Object>>
     */
    @Override
    public PageInfo<Map<String,Object>> selectPage(int pageNum){
        PageHelper.startPage(pageNum, globalConfig.getPageSize()); //开启分页查询，仅第一次查询时生效

        DeviceLendExample example = new DeviceLendExample();
        example.or().andDeletedEqualTo(false);
        List<DeviceLend> res = deviceLendMapper.selectByExample(example);

        List<Map<String,Object>> list = new LinkedList<>();
        for(DeviceLend deviceLend : res) {
            list.add(select(deviceLend.getId()));
        }

        if(!list.isEmpty()) return new PageInfo<>(list);
        return null;
    }
}
