package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.Department;
import edu.uni.labManagement.bean.DepartmentExample;
import edu.uni.labManagement.mapper.DepartmentMapper;
import edu.uni.labManagement.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 查询所有部门的id和name
     * @return List<Map<String,Object>>
     */
    @Override
    public List<Map<String,Object>> queryAllIdAndName() {
        DepartmentExample example = new DepartmentExample();
        example.or().andDeletedEqualTo(false);
        List<Department> list = departmentMapper.selectByExample(example);

        List<Map<String,Object>> res = new LinkedList<>();
        for(Department department : list) {
            Map<String,Object> prop = new HashMap<>();
            prop.put("value", department.getId());
            prop.put("label", department.getName());
            res.add(prop);
        }
        return res;
    }
}
