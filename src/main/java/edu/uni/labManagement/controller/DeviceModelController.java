package edu.uni.labManagement.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.config.GlobalConfig;
import edu.uni.labManagement.bean.DeviceModel;
import edu.uni.labManagement.bean.DeviceModelSlaves;
import edu.uni.labManagement.service.DeviceModelService;
import edu.uni.labManagement.service.DeviceModelSlavesService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/02 4:14
 */
@Api(description = "设备模型模块")
@Controller
@RequestMapping("json/labManagement/deviceModel")
public class DeviceModelController {

	static class CacheNameHelper{
//		lm_deviceModel_{设备父模型id}
		private static final String listByPid = "lm_deviceModel_";
	}

	@Autowired
	private DeviceModelService deviceModelService;
	@Autowired
	private DeviceModelSlavesService deviceModelSlavesService;
	@Autowired
	private RedisCache cache;

	@ApiOperation(value = "创建父设备模型")
//	@ApiImplicitParam(name="deviceModel", value = "设备模型实体类", required = true, dataType = "deviceModel")
	@PostMapping
	@ResponseBody
	public Result createParent(@RequestBody(required = false) DeviceModel deviceModel){
		if(deviceModel != null){
			boolean success = deviceModelService.insertParentDeviceModel(deviceModel);
			if(success){
				cache.deleteByPaterm(CacheNameHelper.listByPid);
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		}
		return Result.build(ResultType.ParamError);
	}

	@ApiOperation(value = "创建子设备模型")
	@PostMapping("{pid}/{amount}")
	@ResponseBody
	public Result createSon(@RequestBody(required = false) DeviceModel deviceModel, @PathVariable long pid, @PathVariable Integer amount){
		if(deviceModel != null){
			boolean success = deviceModelService.insertSonDeviceModel(deviceModel, pid, amount);
			if(success){
				cache.deleteByPaterm(CacheNameHelper.listByPid);
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		}
		return Result.build(ResultType.ParamError);
	}

	@ApiOperation(value = "删除设备模型")
//	@ApiImplicitParam(name="id", value = "设备模型实体类", required = true, dataType = "long", paramType = "path")
	@DeleteMapping("/{id}")
	@ResponseBody
	public Result destroy(@PathVariable Integer id){
		boolean success = deviceModelService.deleted(id);
		if(success) {
			long pid = deviceModelSlavesService.listBySid(id).get(0).getMaterId();
			cache.deleteByPaterm(CacheNameHelper.listByPid + pid);
			return Result.build(ResultType.Success);
		} else {
			return Result.build(ResultType.Failed);
		}
	}

	@ApiOperation(value = "更新设备模型")
//	@ApiImplicitParam(name="deviceModel", value = "设备模型实体类", required = true, dataType = "deviceModel")
	@PutMapping
	@ResponseBody
	public Result update(@RequestBody DeviceModel deviceModel){
		if (deviceModel != null && deviceModel.getId() != null){
			boolean success = deviceModelService.update(deviceModel);
			if(success) {
				long pid = 1;
				try {
					pid = deviceModelSlavesService.listBySid(deviceModel.getId()).get(0).getMaterId();
				} catch (Exception e) {}
				cache.deleteByPaterm(CacheNameHelper.listByPid + pid);
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		} else {
			return Result.build(ResultType.ParamError);
		}
	}

	@ApiOperation(value = "通过父id查询子设备")
//	@ApiImplicitParam(name="pid", value = "父设备ID", required = true, dataType = "long", paramType = "path")
	@GetMapping("{pid}")
	@ResponseBody
	void receive(HttpServletResponse response, @PathVariable Integer pid) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.listByPid + pid;
//		cache.deleteByPaterm(cacheName);
		String json = cache.get(cacheName);
		if(json == null || json == ""){
			List<DeviceModel> deviceModelList = deviceModelService.listByPid(pid);
//			System.out.println(deviceModelList);
			json = Result.build(ResultType.Success).appendData("res", deviceModelList).convertIntoJSON();
			if(deviceModelList != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

}
