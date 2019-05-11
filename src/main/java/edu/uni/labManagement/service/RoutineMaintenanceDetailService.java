package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.RoutineMaintenanceDetail;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/11 1:57
 */
public interface RoutineMaintenanceDetailService {
	/**
	 * 增
	 * @param routineMaintenanceDetail
	 * @return
	 */
	boolean insert(RoutineMaintenanceDetail routineMaintenanceDetail);

	/**
	 * 改
	 * @param routineMaintenanceDetail
	 * @return
	 */
	boolean update(RoutineMaintenanceDetail routineMaintenanceDetail);

	/**
	 * 删
	 * @param id
	 * @return
	 */
	boolean deleted(long id);

	/**
	 * 通过设备维护ID查询设备维护详情
	 * @param routineMaintenanceId
	 * @return
	 */
	List<RoutineMaintenanceDetail> listByRoutineMaintenanceId(long routineMaintenanceId);

	/**
	 * 通过设备ID查询设备维护详情
	 * @param deviceId
	 * @return
	 */
	List<RoutineMaintenanceDetail> listByDeviceId(long deviceId);
}
