package edu.uni.labManagement.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.service.DeviceService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/05 11:21
 */
@Controller
public class DeviceController {
	static class CacheNameHelper{
//		查看所有的设备
		private static final String list_all = "lm_device_listAll";
	}

	@Autowired
	private DeviceService deviceService;
	@Autowired
	private RedisCache cache;

	@ApiOperation(value = "查询所有的设备")
	@GetMapping("listAll")
	@ResponseBody
	public void receive(HttpServletResponse response) throws Exception{

		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.list_all;
		String json = cache.get(cacheName);

		if(json == null || json == ""){
			List<Device> deviceList = deviceService.listAll();
			json = Result.build(ResultType.Success).appendData("res", deviceList).appendData("count", deviceList.size()).convertIntoJSON();
			if(deviceList != null){
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}
}
