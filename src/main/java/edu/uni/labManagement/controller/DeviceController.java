package edu.uni.labManagement.controller;

import com.github.pagehelper.PageInfo;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.service.DeviceService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
}
