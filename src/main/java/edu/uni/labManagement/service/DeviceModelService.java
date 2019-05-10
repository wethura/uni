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

	/**
	 * 改
	 * @param deviceModel
	 * @return
	 */
	boolean update(DeviceModel deviceModel);

	/**
	 * 删
	 * @param id
	 * @return
	 */
	boolean deleted(long id);

	/**
	 * 通过父设备ID查询其子设备
	 * @param id
	 * @return
	 */
	List<DeviceModel> listByPid(long id);

	/**
	 * 通过设备分类查询所有顶级设备
	 * @param id
	 * @return
	 */
	List<DeviceModel> listByCategoryId(long id);
}
