package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.DeviceMoveOut;
import edu.uni.labManagement.bean.DeviceMoveOutExample;
import edu.uni.labManagement.bean.LabDevice;
import edu.uni.labManagement.bean.LabDeviceExample;
import edu.uni.labManagement.config.LabManagementConfig;
import edu.uni.labManagement.mapper.DeviceMoveOutMapper;
import edu.uni.labManagement.mapper.LabDeviceMapper;
import edu.uni.labManagement.service.DeviceMoveOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DeviceMoveOutServiceImpl implements DeviceMoveOutService {
    @Resource
    private DeviceMoveOutMapper deviceMoveOutMapper;

    @Resource
    private LabDeviceMapper labDeviceMapper;

    @Autowired
    private LabManagementConfig globalConfig;

    @Autowired
    private AuthService authService;

    /**
     * 新增设备移出记录
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean insert(Map<String,Object> json) {
        LabDeviceExample example = new LabDeviceExample();
        example.or().andDeviceIdEqualTo(Long.parseLong((String)json.get("deviceId")))
                .andLabIdEqualTo(Long.parseLong((String)json.get("labId")))
                .andDeletedEqualTo(false);
        List<LabDevice> list = labDeviceMapper.selectByExample(example);
        if(list.isEmpty()) return false;
        LabDevice labDevice = list.get(0);

        labDevice.setDeleted(true);
        if(labDeviceMapper.updateByPrimaryKey(labDevice) <= 0) return false;

        DeviceMoveOut deviceMoveOut = new DeviceMoveOut();
        deviceMoveOut.setLabId(labDevice.getLabId());
        deviceMoveOut.setDeviceId(labDevice.getDeviceId());
        deviceMoveOut.setUniversityId(1L);
        deviceMoveOut.setDatetime(new Date());
        deviceMoveOut.setByWho(1L);
        deviceMoveOut.setDeleted(false);
        return deviceMoveOutMapper.insert(deviceMoveOut) > 0;
    }

    /**
     * 删除设备移出记录
     * @param id 主键
     * @return boolean
     */
    @Override
    public boolean delete(Long id){
        DeviceMoveOut deviceMoveOut = new DeviceMoveOut();
        deviceMoveOut.setId(id);
        deviceMoveOut.setDeleted(true);
        return deviceMoveOutMapper.updateByPrimaryKeySelective(deviceMoveOut) > 0;
    }

    /**
     * 修改设备移出记录
     * @param json 请求参数
     * @return boolean
     */
    @Override
    public boolean update(Map<String,Object> json){
        DeviceMoveOut deviceMoveOut = new DeviceMoveOut();
        deviceMoveOut.setId(Long.parseLong((String)json.get("id")));
        deviceMoveOut.setLabId(Long.parseLong((String)json.get("labId")));
        deviceMoveOut.setDeviceId(Long.parseLong((String)json.get("deviceId")));
        DeviceMoveOut deviceMoveOutNew = deviceMoveOutMapper.selectByPrimaryKey(Long.parseLong((String)json.get("Id")));
        deviceMoveOutNew.setId(null);
        deviceMoveOutNew.setDeleted(true);
        deviceMoveOutMapper.insert(deviceMoveOutNew);

        deviceMoveOut.setByWho(((User)authService.getUser()).getId());
        deviceMoveOut.setUniversityId(((User)authService.getUser()).getUniversityId());
        return deviceMoveOutMapper.updateByPrimaryKeySelective(deviceMoveOut) > 0;
    }

    /**
     * 查询单条设备移出记录
     * @param id 主键
     * @return Map<String,Object>
     */
    @Override
    public Map<String,Object> select(Long id){
        DeviceMoveOutExample example = new DeviceMoveOutExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        List<DeviceMoveOut> list = deviceMoveOutMapper.selectByExample(example);
        if(list.isEmpty()) return null;
        DeviceMoveOut deviceMoveOut = list.get(0);

        Map<String,Object> res = new HashMap<>();
        res.put("id", deviceMoveOut.getId());
        res.put("labId", deviceMoveOut.getLabId());
        res.put("deviceId", deviceMoveOut.getDeviceId());
        res.put("datetime", new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(deviceMoveOut.getDatetime()));
        return res;
    }

    /**
     * 分页查询所有设备移出记录
     * @param pageNum 页码
     * @return PageInfo<Map<String,Object>>
     */
    @Override
    public PageInfo<Map<String,Object>> selectPage(int pageNum){
        PageHelper.startPage(pageNum, globalConfig.getPageSize()); //开启分页查询，仅第一次查询时生效

        DeviceMoveOutExample example = new DeviceMoveOutExample();
        example.or().andDeletedEqualTo(false);
        List<DeviceMoveOut> res = deviceMoveOutMapper.selectByExample(example);

        List<Map<String,Object>> list = new LinkedList<>();
        for(DeviceMoveOut deviceMoveOut : res) {
            list.add(select(deviceMoveOut.getId()));
        }

        if(!list.isEmpty()) return new PageInfo<>(list);
        return null;
    }
}
