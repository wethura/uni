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

	boolean insert(DeviceCategory deviceCategory);

	boolean update(DeviceCategory deviceCategory);

	boolean deleted(long id);

	List<DeviceCategory> listAll();
}
