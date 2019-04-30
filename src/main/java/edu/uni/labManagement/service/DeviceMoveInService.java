package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceMoveIn;

import java.util.Map;

public interface DeviceMoveInService {
    /**
     * 新增设备移入记录
     * @param json
     * @return
     */
    boolean insert(Map<String, Object> json);

    /**
     * 根据id查询设备移入记录
     * @param id
     * @return
     */
    DeviceMoveIn select(Long id);
}
