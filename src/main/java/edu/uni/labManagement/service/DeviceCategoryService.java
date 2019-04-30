package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceCategory;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/30 12:49
 */
public interface DeviceCategoryService {

	long insert(DeviceCategory deviceCategory);

	void update(DeviceCategory deviceCategory);



	List<DeviceCategory> listAll();
}
