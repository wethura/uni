package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.MaintenanceRecords;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/04 15:06
 */
public interface MaintenanceRecordsService {

	/**
	 * 增
	 * @param maintenanceRecords
	 * @return
	 */
	boolean insert(MaintenanceRecords maintenanceRecords);

	/**
	 * 改
	 * @param maintenanceRecords
	 */
	boolean update(MaintenanceRecords maintenanceRecords);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	boolean delete(long id);

	/**
	 * 通过设备维修申请ID查询维修记录
	 * @param id
	 * @return
	 */
	MaintenanceRecords listByRepairApplyId(long id);

	/**
	 * 通过设备ID查维修记录
	 * @param id
	 * @return
	 */
	List<MaintenanceRecords> listByDeviceId(long id);

	/**
	 * 通过实验室ID查询维修记录
	 * @param labId
	 * @return
	 */
	List<MaintenanceRecords> listByLabId(long labId);
}
