package edu.uni.labManagement.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.DeviceMoveIn;
import edu.uni.labManagement.service.DeviceMoveInService;
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

@Api(description = "设备管理与实验室管理模块 - 设备移入")
@Controller
@RequestMapping("json/labManagement")
public class DeviceMoveInController {
    @Autowired
    private DeviceMoveInService deviceMoveInService;

    @Autowired
    private RedisCache cache;

    /**
     * 内部类，专门用来管理每个方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // lm_deviceMoveIn_{设备移入记录id}
        private static final String Receive_CacheNamePrefix = "lm_deviceMoveIn_";
        // lm_deviceMoveIn_list_{页码}
        private static final String List_CacheNamePrefix = "lm_deviceMoveIn_list_";
    }

    /**
     * 新增设备移入记录
     * @param json
     * @return
     */
    @ApiOperation(value="新增设备移入记录", notes = "")
    @ApiImplicitParam(name= "json", value = "请求参数", required = true, dataType = "Map")
    @PostMapping("/deviceMoveIn")
    @ResponseBody
    public Result create(@RequestBody(required = false) Map<String,Object> json){
        if(json != null){
            boolean success = deviceMoveInService.insert(json);
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
     * 根据id查询设备移入记录
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "根据id查询设备移入记录", notes = "")
    @ApiImplicitParam(name = "id", value = "路径参数", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/deviceMoveIn/{id}")
    public void receive(@PathVariable Long id,
                        HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
        String json = cache.get(cacheName);
        if(json == null){
            DeviceMoveIn res = deviceMoveInService.select(id);
            json = Result.build(ResultType.Success).appendData("res", res).convertIntoJSON();
            if(res != null){
                cache.set(cacheName, json);
            }
        }
        response.getWriter().write(json);
    }
}
