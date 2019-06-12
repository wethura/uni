package edu.uni.labManagement.service;

import java.util.List;
import java.util.Map;

public interface PFieldService {
    /**
     * 获取所有目的地ID的子列表
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> fieldIdsSonList();
}
