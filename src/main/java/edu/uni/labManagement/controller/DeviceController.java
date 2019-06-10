package edu.uni.labManagement.controller;

import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.service.DeviceService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/05 11:21
 */
@Api(description = "设备模块")
@Controller
@RequestMapping("json/labManagement/device")
public class DeviceController {
	static class CacheNameHelper{
//		查看所有的设备  lm_device_listAll_{pageNum}
		private static final String list_all = "lm_device_listAll_";
//		查看实验室中的设备  lm_device_listByLab_{labId}
		private static final String listByLab = "lm_device_listByLab_";
		// lm_device_{设备id}
		private static final String Receive_CacheNamePrefix = "lm_device_";
		// lm_devices_list_{页码}
		private static final String List_CacheNamePrefix = "lm_devices_list_";
		// lm_devices_listByTwo
		private static final String ListByTwo_CacheName = "lm_devices_listByTwo";
	}

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private RedisCache cache;

	@ApiOperation(value = "查询所有的设备")
	@GetMapping("list/{pageNum}")
	@ResponseBody
	public void receive(HttpServletResponse response, @PathVariable int pageNum) throws Exception{

		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.list_all + pageNum;
		cache.deleteByPaterm(cacheName);
		String json = cache.get(cacheName);

		if(json == null || json == ""){
			PageInfo<Device> pageInfo = deviceService.listAll(pageNum);
			json = Result.build(ResultType.Success).appendData("res", pageInfo).convertIntoJSON();
			if(pageInfo.getSize() != 0){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

	@ApiOperation(value = "查询实验室的所有的设备")
	@GetMapping("listByLab/{labId}")
	@ResponseBody
	public void receive2(HttpServletResponse response, @PathVariable int labId) throws Exception{

		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.listByLab + labId;
		String json = cache.get(cacheName);

		if(json == null || json == ""){
			List<Device> deviceList = deviceService.listAllByLabId(labId);
			json = Result.build(ResultType.Success).appendData("res", deviceList).appendData("count", deviceList.size()).convertIntoJSON();
			if(deviceList != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}



	@ApiOperation(value = "通过父设备的ID查询子设备")
	@GetMapping("listByParentID/{id}")
	@ResponseBody
	public void receive3(HttpServletResponse response, @PathVariable long id) throws Exception{
		response.setContentType("application/json;charset=utf-8");

		List<Device> list = deviceService.selectByParentId(id);
		if (list != null) {
			response.getWriter().write(Result.build(ResultType.Success).appendData("res", list).convertIntoJSON());
		} else {
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
		}
	}

	@ApiOperation(value = "查询不同样的设备名称", notes = "待测试")
	@GetMapping("listName/{labId}")
	@ResponseBody
	public void receive4(HttpServletResponse response, @PathVariable long labId) throws Exception{
		response.setContentType("application/json;charset=utf-8");

		List<String> list = deviceService.selectDistinctDeviceName(labId);
		if (list != null) {
			response.getWriter().write(Result.build(ResultType.Success).appendData("res", list).convertIntoJSON());
		} else {
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
		}
	}

	@ApiOperation(value = "通过对应实验室设备名称查询所有相关设备", notes = "待测试")
	@GetMapping("listByName/{labId}/{name}")
	@ResponseBody
	public void receive5(HttpServletResponse response,@PathVariable long labId, @PathVariable String name) throws Exception{
		response.setContentType("application/json;charset=utf-8");

		List<Device> list = deviceService.listByNameAndLab(name, labId);
		if (list != null) {
			response.getWriter().write(Result.build(ResultType.Success).appendData("res", list).convertIntoJSON());
		} else {
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
		}
	}


	@ApiOperation(value = "根据设备id查询设备详情")
	@GetMapping("listByDeviceId/{deviceId}")
	@ResponseBody
	public void receive6(HttpServletResponse response, @PathVariable long deviceId) throws Exception{

		response.setContentType("application/json;charset=utf-8");

		Device device = deviceService.selectById(deviceId);
		if (device == null || device.getId() == null){
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
		} else {
			response.getWriter().write(Result.build(ResultType.Success).appendData("res", device).convertIntoJSON());
		}
	}

	@ApiOperation(value = "根据设备id查询封装设备详情")
	@GetMapping("listPojoByDeviceId/{deviceId}")
	@ResponseBody
	public void receive7(HttpServletResponse response, @PathVariable long deviceId) throws Exception{

		response.setContentType("application/json;charset=utf-8");

		ExcelDevicePojo pojo = deviceService.selectPojoById(deviceId);
		if (pojo == null || pojo.getId() == null){
			response.getWriter().write(Result.build(ResultType.Failed).convertIntoJSON());
		} else {
			response.getWriter().write(Result.build(ResultType.Success).appendData("res", pojo).convertIntoJSON());
		}
	}


	/**
	 * 查询所有设备的id和name
	 * @param response
	 * @throws IOException
	 */
	@ApiOperation(value = "查询所有设备的id和name", notes = "")
	@GetMapping("devices/listByTwo")
	public void listByTwo(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.ListByTwo_CacheName;
		String json = cache.get(cacheName);
		if(json == null){
			List<Map<String,Object>> list = deviceService.selectByTwo();
			json = Result.build(ResultType.Success).appendData("list", list).convertIntoJSON();
			if(list != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

	/**
	 * 删除设备listByTwo的缓存
	 * @return Result
	 */
	@ApiOperation(value = "删除设备listByTwo的缓存", notes = "")
	@DeleteMapping("devices/listByTwo")
	@ResponseBody
	public Result destroyByTwo(){
		cache.delete(CacheNameHelper.ListByTwo_CacheName);
		return Result.build(ResultType.Success);
	}


}
