package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceModel;
import edu.uni.labManagement.bean.DeviceModelSlaves;

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
	 * @param userid
	 * @param universityid
	 * @param categoryid
	 * @return
	 */
	long insertParentDeviceModel(DeviceModel deviceModel, long userid, long universityid, long categoryid);


	/**
	 * 说明：用于创建子模板
	 * @param deviceModel
	 * @param pid
	 * @param amount
	 * @param userid
	 * @param universityid
	 * @param categoryid
	 * @return
	 */
	long insertSonDeviceModel(DeviceModel deviceModel, long pid, int amount, long userid, long universityid, long categoryid);
}
