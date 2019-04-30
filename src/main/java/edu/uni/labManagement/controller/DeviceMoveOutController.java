package edu.uni.labManagement.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.DeviceMoveOut;
import edu.uni.labManagement.service.DeviceMoveOutService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Api(description = "设备管理与实验室管理模块 - 设备移出")
@Controller
@RequestMapping("json/labManagement")
public class DeviceMoveOutController {
    @Autowired
    private DeviceMoveOutService deviceMoveOutService;

    @Autowired
    private RedisCache cache;

    /**
     * 内部类，专门用来管理每个方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // lm_deviceMoveOut_{设备移出记录id}
        private static final String Receive_CacheNamePrefix = "lm_deviceMoveOut_";
        // lm_deviceMoveOut_list_{页码}
        private static final String List_CacheNamePrefix = "lm_deviceMoveOut_list_";
    }

    /**
     * 新增设备移出记录
     * @param json
     * @return
     */
    @ApiOperation(value="新增设备移出记录", notes = "")
    @ApiImplicitParam(name= "json", value = "请求参数", required = true, dataType = "Map")
    @PostMapping("/deviceMoveOut")
    @ResponseBody
    public Result create(@RequestBody(required = false) Map<String,Object> json){
        if(json != null){
            boolean success = deviceMoveOutService.insert(json);
            if(success){
                cache.deleteByPaterm(CacheNameHelper.List_CacheNamePrefix + "*");
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 根据id查询设备移出记录
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "根据id查询设备移出记录", notes = "")
    @ApiImplicitParam(name = "id", value = "路径参数", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/deviceMoveOut/{id}")
    public void receive(@PathVariable Long id,
                        HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
        String json = cache.get(cacheName);
        if(json == null){
            DeviceMoveOut res = deviceMoveOutService.select(id);
            json = Result.build(ResultType.Success).appendData("res", res).convertIntoJSON();
            if(res != null){
                cache.set(cacheName, json);
            }
        }
        response.getWriter().write(json);
    }
}
