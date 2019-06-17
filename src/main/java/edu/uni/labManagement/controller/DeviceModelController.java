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
import java.util.Map;

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
		private static String base = "lm_deviceModel_*";
//		lm_deviceModel_{设备父模型id}
		private static final String listByPid = "lm_deviceModel_byPid_";
		private static final String listByCategoryId = "lm_deviceModel_byCategoryId_";
		// lm_deviceModel_{设备型号id}
		private static final String Receive_CacheNamePrefix = "lm_deviceModel_";
		// lm_deviceModels_list_{页码}
		private static final String List_CacheNamePrefix = "lm_deviceModels_list_";
		// lm_deviceModels_listByTwo
		private static final String ListByTwo_CacheName = "lm_deviceModels_listByTwo";
	}

	@Autowired
	private DeviceModelService deviceModelService;
	@Autowired
	private DeviceModelSlavesService deviceModelSlavesService;
	@Autowired
	private RedisCache cache;

	@ApiOperation(value = "创建父设备模型", notes = "已测试")
//	@ApiImplicitParam(name="deviceModel", value = "设备模型实体类", required = true, dataType = "deviceModel")
	@PostMapping
	@ResponseBody
	public Result createParent(@RequestBody(required = false) DeviceModel deviceModel){
		if(deviceModel != null){
			boolean success = deviceModelService.insertParentDeviceModel(deviceModel);
			if(success){
				cache.deleteByPaterm(CacheNameHelper.base);
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		}
		return Result.build(ResultType.ParamError);
	}

	@ApiOperation(value = "创建子设备模型", notes = "已测试")
	@PostMapping("{pid}/{amount}")
	@ResponseBody
	public Result createSon(@RequestBody(required = false) DeviceModel deviceModel, @PathVariable long pid, @PathVariable Integer amount){
		if(deviceModel != null){
			boolean success = deviceModelService.insertSonDeviceModel(deviceModel, pid, amount);
			if(success){
				cache.deleteByPaterm(CacheNameHelper.base);
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		}
		return Result.build(ResultType.ParamError);
	}

	@ApiOperation(value = "删除设备模型", notes = "已测试")
//	@ApiImplicitParam(name="id", value = "设备模型实体类", required = true, dataType = "long", paramType = "path")
	@DeleteMapping("/{id}")
	@ResponseBody
	public Result destroy(@PathVariable Integer id){
		boolean success = deviceModelService.deleted(id);
		if(success) {
			cache.deleteByPaterm(CacheNameHelper.base);
			return Result.build(ResultType.Success);
		} else {
			return Result.build(ResultType.Failed);
		}
	}

	@ApiOperation(value = "更新设备模型", notes = "已测试")
//	@ApiImplicitParam(name="deviceModel", value = "设备模型实体类", required = true, dataType = "deviceModel")
	@PutMapping
	@ResponseBody
	public Result update(@RequestBody DeviceModel deviceModel){
		if (deviceModel != null && deviceModel.getId() != null){
			boolean success = deviceModelService.update(deviceModel);
			if(success) {
				cache.deleteByPaterm(CacheNameHelper.base);
				return Result.build(ResultType.Success);
			} else {
				return Result.build(ResultType.Failed);
			}
		} else {
			return Result.build(ResultType.ParamError);
		}
	}

	@ApiOperation(value = "通过父id查询子设备", notes = "已测试")
//	@ApiImplicitParam(name="pid", value = "父设备ID", required = true, dataType = "long", paramType = "path")
	@GetMapping("/byPid/{pid}")
	@ResponseBody
	void receive1(HttpServletResponse response, @PathVariable Integer pid) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.listByPid + pid;
		String json = cache.get(cacheName);
		if(json == null || json == "") {
			List<DeviceModel> deviceModelList = deviceModelService.listByPid(pid);
			json = Result.build(ResultType.Success).appendData("res", deviceModelList).convertIntoJSON();
			if(deviceModelList != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

	@ApiOperation(value = "通过分类id查询父设备", notes = "已测试")
	@GetMapping("/byCategoryId/{categoryId}")
	@ResponseBody
	void receive2(HttpServletResponse response, @PathVariable Integer categoryId) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.listByCategoryId + categoryId;
		String json = cache.get(cacheName);
		if(json == null || json == "") {
			List<DeviceModel> deviceModelList = deviceModelService.listByCategoryId(categoryId);
			json = Result.build(ResultType.Success).appendData("res", deviceModelList).convertIntoJSON();
			if(deviceModelList!=null) {
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}
	/**
	 * 查询所有设备型号的id和name
	 * @param response
	 * @throws IOException
	 */
	@ApiOperation(value = "查询所有设备型号的id和name", notes = "")
	@GetMapping("deviceModels/listByTwo")
	public void listByTwo(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.ListByTwo_CacheName;
		String json = cache.get(cacheName);
		if(json == null){
			List<Map<String,Object>> list = deviceModelService.selectByTwo();
			json = Result.build(ResultType.Success).appendData("list", list).convertIntoJSON();
			if(list != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

	/**
	 * 删除设备型号listByTwo的缓存
	 * @return Result
	 */
	@ApiOperation(value = "删除设备型号listByTwo的缓存", notes = "")
	@DeleteMapping("deviceModels/listByTwo")
	@ResponseBody
	public Result destroyByTwo(){
		cache.deleteByPaterm(CacheNameHelper.base);
		return Result.build(ResultType.Success);
	}

	@ApiOperation(value = "通过ID查询模型详情")
	@GetMapping("deviceModels/getFromId/{deviceModelId}")
	@ResponseBody
	public Result getFromId(@PathVariable Long deviceModelId){
		Map<String, Object> ret= deviceModelService.getFromDeviceModelId(deviceModelId);
		if (ret == null){
			return Result.build(ResultType.Failed);
		}else {
			return Result.build(ResultType.Success).appendData("res", ret);
		}
	}


	@ApiOperation(value = "通过ID查询模型详情")
	@GetMapping("deviceModels/getAll")
	@ResponseBody
	public Result getAll(){
		List<Map<String, Object>> ret= deviceModelService.getAll();
		if (ret == null){
			return Result.build(ResultType.Failed);
		}else {
			return Result.build(ResultType.Success).appendData("res", ret);
		}
	}
}
