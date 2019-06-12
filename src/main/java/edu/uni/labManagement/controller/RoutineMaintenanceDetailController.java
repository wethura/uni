package edu.uni.labManagement.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.labManagement.bean.RoutineMaintenanceDetail;
import edu.uni.labManagement.service.RoutineMaintenanceDetailService;
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
 * @date 2019/05/11 2:19
 */
@Api(value = "设备维护详情模块")
@Controller
@RequestMapping("json/labManagement/routineMaintenanceDetail")
public class RoutineMaintenanceDetailController {

	static class CacheNameHelper{
		private static final String base = "lm_routineMaintenanceDetail_*";
		//		lm_routineMaintenanceDetail_DeviceId_{deviceId}
		private static final String LIST_BY_DEVICEID = "lm_routineMaintenanceDetail_DeviceId_";
		//		lm_routineMaintenanceDetail_LabId_{labId}
		private static final String LIST_BY_MAINTENANCE_ID = "lm_routineMaintenanceDetail_RoutineMaintenanceId_";
	}

	@Autowired
	private RoutineMaintenanceDetailService routineMaintenanceDetailService;
	@Autowired
	private RedisCache cache;

	@ApiOperation(value = "通过设备ID查询设备维护详情", notes = "待测试")
	@GetMapping("/listByDeviceId/{deviceId}")
	@ResponseBody
	public void receive(HttpServletResponse response, @PathVariable long deviceId) throws Exception{
		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.LIST_BY_DEVICEID + deviceId;
		String json = cache.get(cacheName);

		if (json == null || json == "") {
			List<RoutineMaintenanceDetail> details = routineMaintenanceDetailService.listByDeviceId(deviceId);
			json = Result.build(ResultType.Success).appendData("res", details).convertIntoJSON();
			if (details != null) {
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}

	@ApiOperation(value = "通过维护详情ID查询设备维护详情", notes = "待测试")
	@GetMapping("/listByRoutineMaintenanceId/{routineMaintenanceId}")
	@ResponseBody
	public void receive2(HttpServletResponse response, @PathVariable long routineMaintenanceId) throws Exception{
		response.setContentType("application/json;charset=utf-8");
		String cacheName = CacheNameHelper.LIST_BY_MAINTENANCE_ID + routineMaintenanceId;
		cache.deleteByPaterm(cacheName);
		String json = cache.get(cacheName);

		if (json == null || json == "") {
			List<RoutineMaintenanceDetail> details = routineMaintenanceDetailService.listByRoutineMaintenanceId(routineMaintenanceId);
			json = Result.build(ResultType.Success).appendData("res", details).convertIntoJSON();
			if (details != null) {
				cache.set(cacheName, json);
			}
		}
		response.getWriter().write(json);
	}
}
