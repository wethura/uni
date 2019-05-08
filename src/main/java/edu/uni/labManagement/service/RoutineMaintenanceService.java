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

	boolean insert(RoutineMaintenance routineMaintenance);

	boolean update(RoutineMaintenance routineMaintenance);

	boolean deleted(long id);

	List<RoutineMaintenance> listByDeviceId(long deviceId);

	List<RoutineMaintenance> listByLabId(long labId);
}
