package edu.uni.labManagement.controller;

import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.service.DeviceMoveOutService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Api(description = "设备管理与实验室管理模块 - 设备移出")
@Controller
@RequestMapping("json/labManagement")
public class DeviceMoveOutController {
    @Resource
    private DeviceMoveOutService deviceMoveOutService;

    @Resource
    private RedisCache cache;

    /**
     * 内部类，专门用来管理每个方法所对应缓存的名称。
     */
    private static class CacheNameHelper{
        private static final String base = "lm_deviceMoveOut_*";
        // lm_deviceMoveOut_{设备移出记录id}
        private static final String Receive_CacheNamePrefix = "lm_deviceMoveOut_";
        // lm_deviceMoveOuts_list_{页码}
        private static final String List_CacheNamePrefix = "lm_deviceMoveOut_list_";
    }

    /**
     * 新增设备移出记录
     * @param json 请求参数
     * @return Result
     */
    @ApiOperation(value="新增设备移出记录", notes = "")
    @ApiImplicitParam(name= "json", value = "请求参数", required = true, dataType = "Map")
    @PostMapping("deviceMoveOut")
    @ResponseBody
    public Result create(@RequestBody(required = false) Map<String,Object> json){
        if(json != null){
            boolean success = deviceMoveOutService.insert(json);
            if(success){
                cache.deleteByPaterm(CacheNameHelper.base);
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 删除设备移出记录
     * @param id 主键
     * @return Result
     */
    @ApiOperation(value = "删除设备移出记录", notes = "")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long", paramType = "path")
    @DeleteMapping("deviceMoveOut/{id}")
    @ResponseBody
    public Result destroy(@PathVariable Long id){
        boolean success = deviceMoveOutService.delete(id);
        if(success){
            cache.deleteByPaterm(CacheNameHelper.base);
            return Result.build(ResultType.Success);
        }else{
            return Result.build(ResultType.Failed);
        }
    }

    /**
     * 修改设备移出记录
     * @param json 请求参数
     * @return Result
     */
    @ApiOperation(value="修改设备移出记录")
    @ApiImplicitParam(name="json", value = "请求参数", required = true, dataType = "Map")
    @PutMapping("deviceMoveOut")
    @ResponseBody
    public Result update(@RequestBody(required = false) Map<String,Object> json){
        if(json != null && json.get("id") != null){
            boolean success = deviceMoveOutService.update(json);
            if(success){
                cache.deleteByPaterm(CacheNameHelper.base);
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 查询单条设备移出记录
     * @param id 主键
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "查询单条设备移出记录", notes = "")
    @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long", paramType = "path")
    @GetMapping("deviceMoveOut/{id}")
    public void receive(@PathVariable Long id,
                        HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
        String json = cache.get(cacheName);
        if(json == null){
            Map<String,Object> res = deviceMoveOutService.select(id);
            json = Result.build(ResultType.Success).appendData("res", res).convertIntoJSON();
            if(res != null){
                cache.set(cacheName, json);
            }
        }
        response.getWriter().write(json);
    }

    /**
     * 分页查询所有设备移出记录
     * @param pageNum 页码
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "分页查询所有设备移出记录", notes = "")
    @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer", paramType = "path")
    @GetMapping(value = "/deviceMoveOuts/list/{pageNum}")
    public void list(
            @PathVariable Integer pageNum ,
            HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.List_CacheNamePrefix + pageNum;
        String json = cache.get(cacheName);
        if(json == null){
            PageInfo<Map<String,Object>> pageInfo = deviceMoveOutService.selectPage(pageNum);
            json = Result.build(ResultType.Success).appendData("pageInfo", pageInfo).convertIntoJSON();
            if(pageInfo != null) {
                cache.set(cacheName, json);
            }
        }
        response.getWriter().write(json);
    }

    /**
     * 删除所有设备移出记录缓存
     * @return Result
     */
    @ApiOperation(value = "删除所有设备移出记录缓存", notes = "")
    @DeleteMapping("deviceMoveOuts/cache")
    @ResponseBody
    public Result destroyCache(){
        cache.deleteByPaterm(CacheNameHelper.base);
        return Result.build(ResultType.Success);
    }
}
