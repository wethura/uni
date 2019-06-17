package edu.uni.labManagement.controller;

import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.service.DeviceLendReturnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Api(description = "设备管理与实验室管理模块 - 设备借出归还")
@Controller
@RequestMapping("json/labManagement")
public class DeviceLendReturnController {
    @Resource
    private DeviceLendReturnService deviceLendReturnService;

    /**
     * 借出设备
     * @param json 请求参数
     * @return Result
     */
    @ApiOperation(value="借出设备", notes = "")
    @ApiImplicitParam(name= "json", value = "请求参数", required = true, dataType = "Map")
    @PostMapping("deviceLendReturn")
    @ResponseBody
    public Result create(@RequestBody(required = false) Map<String,Object> json) throws Exception {
        if(json != null){
            boolean success = deviceLendReturnService.insert(json);
            if(success){
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 审批借出申请
     * @param json 请求参数
     * @return Result
     */
    @ApiOperation(value="审批借出申请")
    @ApiImplicitParam(name="json", value = "请求参数", required = true, dataType = "Map")
    @PutMapping("deviceLendReturn/approval")
    @ResponseBody
    public Result updateApproval(@RequestBody(required = false) Map<String,Object> json){
        if(json != null && json.get("id") != null){
            boolean success = deviceLendReturnService.updateApproval(json);
            if(success){
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 归还设备
     * @param json 请求参数
     * @return Result
     */
    @ApiOperation(value="归还设备")
    @ApiImplicitParam(name="json", value = "请求参数", required = true, dataType = "Map")
    @PutMapping("deviceLendReturn/return")
    @ResponseBody
    public Result updateReturned(@RequestBody(required = false) Map<String,Object> json){
        if(json != null && json.get("id") != null){
            boolean success = deviceLendReturnService.updateReturned(json);
            if(success){
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 分页查询可审批的借出申请
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     */
    @ApiOperation(value = "分页查询可审批的借出申请", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "页码", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name="pageSize", value = "每页显示条数", required = true, dataType = "Integer", paramType = "path")
    })
    @GetMapping(value = "deviceLendReturns/list/approval/{pageNum}/{pageSize}")
    public void selectPageApproval(
            @PathVariable Integer pageNum ,
            @PathVariable Integer pageSize,
            HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PageInfo<Map<String,Object>> pageInfo = deviceLendReturnService.selectPageApproval(pageNum, pageSize);
        String json = Result.build(ResultType.Success).appendData("pageInfo", pageInfo).convertIntoJSON();
        response.getWriter().write(json);
    }

    /**
     * 分页查询可归还的设备
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     */
    @ApiOperation(value = "分页查询可归还的设备", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum", value = "页码", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name="pageSize", value = "每页显示条数", required = true, dataType = "Integer", paramType = "path")
    })
    @GetMapping(value = "deviceLendReturns/list/return/{pageNum}/{pageSize}")
    public void selectPageReturned(
            @PathVariable Integer pageNum ,
            @PathVariable Integer pageSize,
            HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PageInfo<Map<String,Object>> pageInfo = deviceLendReturnService.selectPageReturned(pageNum, pageSize);
        String json = Result.build(ResultType.Success).appendData("pageInfo", pageInfo).convertIntoJSON();
        response.getWriter().write(json);
    }

    /**
     * 分页高级筛选可归还的设备
     * @param json 请求参数
     */
    @ApiOperation(value = "分页高级筛选可归还的设备", notes = "")
    @ApiImplicitParam(name= "json", value = "请求参数", required = true, dataType = "Map")
    @PostMapping("deviceLendReturns/filter/return")
    public void filterReturned(@RequestBody(required = false) Map<String,Object> json,
            HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PageInfo<Map<String,Object>> pageInfo = deviceLendReturnService.filterReturned(json);
        String res = Result.build(ResultType.Success).appendData("pageInfo", pageInfo).convertIntoJSON();
        response.getWriter().write(res);
    }
}
