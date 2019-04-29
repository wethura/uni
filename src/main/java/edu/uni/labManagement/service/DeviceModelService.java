package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceModel;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/30 1:50
 */
public interface DeviceModelService {
	/**
	 * 说明：仅用于创建父模板
	 * @param deviceModel
	 */
	long insertSlaveDeviceModel(DeviceModel deviceModel);

	long insertParentDeviceModel(DeviceModel deviceModel, long pid);
}
