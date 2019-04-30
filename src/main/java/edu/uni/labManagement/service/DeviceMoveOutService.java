package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceMoveOut;

import java.util.Map;

public interface DeviceMoveOutService {
    /**
     * 新增设备移出记录
     * @param json
     * @return
     */
    boolean insert(Map<String, Object> json);

    /**
     * 根据id查询设备移出记录
     * @param id
     * @return
     */
    DeviceMoveOut select(Long id);
}
