package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceModelSlaves;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/30 13:26
 */
public interface DeviceModelSlavesService {
	long insert(DeviceModelSlaves deviceModelSlaves);

	void update(DeviceModelSlaves deviceModelSlaves);

	void delete(DeviceModelSlaves deviceModelSlaves);

	List<DeviceModelSlaves> list(DeviceModelSlaves deviceModelSlaves);
}
