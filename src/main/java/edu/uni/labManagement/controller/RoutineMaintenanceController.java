package edu.uni.labManagement.controller;

import edu.uni.labManagement.service.RoutineMaintenanceService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/06 1:15
 */
@Api(description = "日常维护模块")
@Controller
@RequestMapping("json/labManagement/routineMaintenance")
public class RoutineMaintenanceController {
	static class CacheNameHelper{
//		lm_routineMaintenance_DeviceId_{deviceId}
		private static final String listByDeviceId = "lm_routineMaintenance_DeviceId_";
//		lm_routineMaintenance_LabId_{labId}
		private static final String listByLabId = "lm_routineMaintenance_LabId_";
	}

	@Autowired
	private RedisCache cache;
	@Autowired
	private RoutineMaintenanceService routineMaintenanceService;



}
