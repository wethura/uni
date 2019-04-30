package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.DeviceMoveOut;
import edu.uni.labManagement.mapper.DeviceMoveOutMapper;
import edu.uni.labManagement.service.DeviceMoveOutService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class DeviceMoveOutServiceImpl implements DeviceMoveOutService {
    @Resource
    private DeviceMoveOutMapper deviceMoveOutMapper;

    /**
     * 新增设备移出记录
     * @param json
     * @return
     */
    public boolean insert(Map<String,Object> json) {
        DeviceMoveOut deviceMoveOut = new DeviceMoveOut();
        deviceMoveOut.setLabId(Long.parseLong((String)json.get("labId")));
        deviceMoveOut.setDeviceId(Long.parseLong((String)json.get("deviceId")));
        deviceMoveOut.setUniversityId(Long.parseLong((String)json.get("universityId")));
        deviceMoveOut.setDatetime(LocalDateTime.now());
        deviceMoveOut.setByWho(Long.parseLong((String)json.get("byWho")));
        deviceMoveOut.setDeleted(true);
        if(deviceMoveOutMapper.insert(deviceMoveOut) <= 0) return false;
        return true;
    }

    /**
     * 根据id查询设备移出记录
     * @param id
     * @return
     */
    public DeviceMoveOut select(Long id){
        return deviceMoveOutMapper.selectByPrimaryKey(id);
    }
}
