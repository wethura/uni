package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceRepairApply;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/03 23:51
 */
public interface DeviceRepairApplyService {

	/**
	 * 增
	 * @param deviceRepairApply
	 * @return
	 */
	boolean insert(DeviceRepairApply deviceRepairApply);

	/**
	 * 改
	 * @param deviceRepairApply
	 * @return
	 */
	boolean update(DeviceRepairApply deviceRepairApply);

	/**
	 * 删
	 * @param id
	 * @return
	 */
	boolean deleted(long id);

	/**
	 * 通过设备维修申请状态查询设备维修申请
	 * @param states
	 * @return
	 */
	List<DeviceRepairApply> listByStates(int states);

	/**
	 * 通过设备ID查询维修申请
	 * @param id
	 * @return
	 */
	List<DeviceRepairApply> listByDeviceId(long id);

	/**
	 * 通过设备维修申请ID查询设备维修申请
	 * @param id
	 * @return
	 */
	DeviceRepairApply listById(long id);
}
