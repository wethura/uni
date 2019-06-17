package edu.uni.labManagement.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.service.PFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(description = "设备管理与实验室管理模块 - 场地")
@Controller
@RequestMapping("json/labManagement")
public class PFieldController {
    @Resource
    private PFieldService pFieldService;

    /**
     * 获取所有目的地ID的子列表
     */
    @ApiOperation(value = "获取所有目的地ID的子列表", notes = "")
    @GetMapping("pFields/fieldIdsSonList")
    public void fieldIdsSonList(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        List<Map<String,Object>> list = pFieldService.fieldIdsSonList();
        String json = Result.build(ResultType.Success).appendData("list", list).convertIntoJSON();
        response.getWriter().write(json);
    }
}
