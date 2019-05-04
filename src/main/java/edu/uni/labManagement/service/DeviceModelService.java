package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceModel;
import edu.uni.labManagement.bean.DeviceModelSlaves;

import java.util.List;

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
	 * @return
	 */
	boolean insertParentDeviceModel(DeviceModel deviceModel);


	/**
	 * 说明：用于创建子模板
	 * @param deviceModel
	 * @param pid
	 * @return
	 */
	boolean insertSonDeviceModel(DeviceModel deviceModel, long pid, Integer amount);

	boolean update(DeviceModel deviceModel);

	boolean deleted(long id);

	List<DeviceModel> listByPid(long id);
}
