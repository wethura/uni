package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.labManagement.bean.DeviceMoveIn;
import edu.uni.labManagement.bean.DeviceMoveInExample;
import edu.uni.labManagement.bean.LabDevice;
import edu.uni.labManagement.bean.LabDeviceExample;
import edu.uni.labManagement.config.LabManagementConfig;
import edu.uni.labManagement.mapper.DeviceMoveInMapper;
import edu.uni.labManagement.mapper.LabDeviceMapper;
import edu.uni.labManagement.service.DeviceMoveInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DeviceMoveInServiceImpl implements DeviceMoveInService {
    @Resource
    private DeviceMoveInMapper deviceMoveInMapper;

    @Resource
    private LabDeviceMapper labDeviceMapper;

    @Autowired
    private LabManagementConfig globalConfig;

    /**
     * 新增设备移入记录
     * @param json 请求参数
     * @return boolean
     */
    public boolean insert(Map<String,Object> json) {
        DeviceMoveIn deviceMoveIn = new DeviceMoveIn();
        deviceMoveIn.setLabId(Long.parseLong((String)json.get("LabId")));
        deviceMoveIn.setDeviceId(Long.parseLong((String)json.get("DeviceId")));
        deviceMoveIn.setUniversityId(1L);
        deviceMoveIn.setDatetime(new Date());
        deviceMoveIn.setByWho(1L);
        deviceMoveIn.setDeleted(false);
        if(deviceMoveInMapper.insert(deviceMoveIn) <= 0) return false;

        LabDevice labDevice = new LabDevice();
        labDevice.setDeviceId(deviceMoveIn.getDeviceId());
        labDevice.setLabId(deviceMoveIn.getLabId());
        labDevice.setUniversityId(deviceMoveIn.getUniversityId());
        labDevice.setDatetime(deviceMoveIn.getDatetime());
        labDevice.setByWho(deviceMoveIn.getByWho());
        labDevice.setDeleted(deviceMoveIn.getDeleted());
        return labDeviceMapper.insert(labDevice) > 0;
    }

    /**
     * 删除设备移入记录
     * @param id 主键
     * @return boolean
     */
    public boolean delete(Long id){
        DeviceMoveIn deviceMoveIn = new DeviceMoveIn();
        deviceMoveIn.setId(id);
        deviceMoveIn.setDeleted(true);
        return deviceMoveInMapper.updateByPrimaryKeySelective(deviceMoveIn) > 0;
    }

    /**
     * 修改设备移入记录
     * @param json 请求参数
     * @return boolean
     */
    public boolean update(Map<String,Object> json){
        DeviceMoveIn deviceMoveIn = new DeviceMoveIn();
        deviceMoveIn.setId(Long.parseLong((String)json.get("Id")));
        deviceMoveIn.setLabId(Long.parseLong((String)json.get("LabId")));
        deviceMoveIn.setDeviceId(Long.parseLong((String)json.get("DeviceId")));
        return deviceMoveInMapper.updateByPrimaryKeySelective(deviceMoveIn) > 0;
    }

    /**
     * 查询单条设备移入记录
     * @param id 主键
     * @return Map<String,Object>
     */
    public Map<String,Object> select(Long id){
        DeviceMoveInExample example = new DeviceMoveInExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<DeviceMoveIn> list = deviceMoveInMapper.selectByExample(example);
        if(list.isEmpty()) return null;
        DeviceMoveIn deviceMoveIn = list.get(0);

        Map<String,Object> res = new HashMap<>();
        res.put("id", deviceMoveIn.getId());
        res.put("labId", deviceMoveIn.getLabId());
        res.put("deviceId", deviceMoveIn.getDeviceId());
        res.put("datetime", new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(deviceMoveIn.getDatetime()));
        return res;
    }

    /**
     * 分页查询所有设备移入记录
     * @param pageNum 页码
     * @return PageInfo<Map<String,Object>>
     */
    public PageInfo<Map<String,Object>> selectPage(int pageNum){
        PageHelper.startPage(pageNum, globalConfig.getPageSize()); //开启分页查询，仅第一次查询时生效

        DeviceMoveInExample example = new DeviceMoveInExample();
        example.or().andDeletedEqualTo(false);
        List<DeviceMoveIn> res = deviceMoveInMapper.selectByExample(example);

        List<Map<String,Object>> list = new LinkedList<>();
        for(DeviceMoveIn deviceMoveIn : res) {
            list.add(select(deviceMoveIn.getId()));
        }

        if(!list.isEmpty()) return new PageInfo<>(list);
        return null;
    }
}
