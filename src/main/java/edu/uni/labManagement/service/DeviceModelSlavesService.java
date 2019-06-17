package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceModelSlaves;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/30 13:26
 */
@Service
public interface DeviceModelSlavesService {
	/**
	 * 增
	 * @param deviceModelSlaves
	 * @return
	 */
	boolean insert(DeviceModelSlaves deviceModelSlaves);

	/**
	 * 改
	 * @param deviceModelSlaves
	 */
	boolean update(DeviceModelSlaves deviceModelSlaves);

	/**
	 * 删
	 * @param deviceModelSlaves
	 */
	boolean delete(DeviceModelSlaves deviceModelSlaves);

	/**
	 * 通过父ID查
	 * @param pid
	 * @return
	 */
	List<DeviceModelSlaves> listByPid(long pid);

	/**
	 * 通过子ID查
	 * @param sid
	 * @return
	 */
	List<DeviceModelSlaves> listBySid(long sid);
}
