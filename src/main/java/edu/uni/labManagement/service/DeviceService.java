package edu.uni.labManagement.service;

import com.github.pagehelper.PageInfo;
import edu.uni.labManagement.bean.Device;

import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/05 11:23
 */
public interface DeviceService {
	/**
	 * 分页查询所有顶级设备
	 * @param pageNum
	 * @return
	 */
	PageInfo<Device> listAll(int pageNum);

	/**
	 * 查询实验室的所有顶级设备
	 * @param labId
	 * @return
	 */
	List<Device> listAllByLabId(long labId);

	List<Device> selectByParentId(long id);

	List<String> selectDistinctDeviceName(long labId);

	List<Device> listByNameAndLab(String name, long labId);

	/**
	 * 查询所有设备的id和name
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> selectByTwo();
}
