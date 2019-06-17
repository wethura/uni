package edu.uni.labManagement.service;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    /**
     * 查询所有部门的id和name
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> queryAllIdAndName();
}
