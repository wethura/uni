package edu.uni.labManagement.service;

import com.github.pagehelper.PageInfo;
import edu.uni.labManagement.bean.DeviceRepairApply;
import edu.uni.labManagement.pojo.DeviceRepairApplyPojo;

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
	 * @param pageNum
	 * @return
	 */
	PageInfo<DeviceRepairApplyPojo> listByStates(int states, int pageNum);

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
