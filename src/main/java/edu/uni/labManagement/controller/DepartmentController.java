package edu.uni.labManagement.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(description = "设备管理与实验室管理模块 - 部门")
@Controller
@RequestMapping("json/labManagement")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    /**
     * 查询所有部门的id和name
     */
    @ApiOperation(value = "查询所有部门的id和name", notes = "")
    @GetMapping("department/queryAllIdAndName")
    public void queryAllIdAndName(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        List<Map<String,Object>> list = departmentService.queryAllIdAndName();
        String json = Result.build(ResultType.Success).appendData("list", list).convertIntoJSON();
        response.getWriter().write(json);
    }
}
