package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.DeviceMoveIn;
import edu.uni.labManagement.mapper.DeviceMoveInMapper;
import edu.uni.labManagement.service.DeviceMoveInService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class DeviceMoveInServiceImpl implements DeviceMoveInService {
    @Resource
    private DeviceMoveInMapper deviceMoveInMapper;

    /**
     * 新增设备移入记录
     * @param json
     * @return
     */
    public boolean insert(Map<String,Object> json) {
        DeviceMoveIn deviceMoveIn = new DeviceMoveIn();
        deviceMoveIn.setLabId(Long.parseLong((String)json.get("labId")));
        deviceMoveIn.setDeviceId(Long.parseLong((String)json.get("deviceId")));
        deviceMoveIn.setUniversityId(Long.parseLong((String)json.get("universityId")));
        deviceMoveIn.setDatetime(LocalDateTime.now());
        deviceMoveIn.setByWho(Long.parseLong((String)json.get("byWho")));
        deviceMoveIn.setDeleted(true);
        if(deviceMoveInMapper.insert(deviceMoveIn) <= 0) return false;
        return true;
    }

    /**
     * 根据id查询设备移入记录
     * @param id
     * @return
     */
    public DeviceMoveIn select(Long id){
        return deviceMoveInMapper.selectByPrimaryKey(id);
    }
}
