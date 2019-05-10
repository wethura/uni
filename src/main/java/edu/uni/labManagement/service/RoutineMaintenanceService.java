package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.RoutineMaintenance;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/06 0:11
 */
public interface RoutineMaintenanceService {

	/**
	 * 增
	 * @param routineMaintenance
	 * @return
	 */
	boolean insert(RoutineMaintenance routineMaintenance);

	/**
	 * 改
	 * @param routineMaintenance
	 * @return
	 */
	boolean update(RoutineMaintenance routineMaintenance);

	/**
	 * 删
	 * @param id
	 * @return
	 */
	boolean deleted(long id);

	/**
	 * 通过设备ID查询日常维护
	 * @param deviceId
	 * @return
	 */
	List<RoutineMaintenance> listByDeviceId(long deviceId);

	/**
	 * 通过实验室ID查询维护记录
	 * @param labId
	 * @return
	 */
	List<RoutineMaintenance> listByLabId(long labId);
}
