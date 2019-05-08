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

	boolean insert(DeviceRepairApply deviceRepairApply);

	boolean update(DeviceRepairApply deviceRepairApply);

	boolean deleted(long id);

	List<DeviceRepairApply> listByStates(int states);

	List<DeviceRepairApply> listByDeviceId(long id);

	DeviceRepairApply listById(long id);
}
