package edu.uni.labManagement.service;

import edu.uni.labManagement.bean.DeviceCategory;

import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/30 12:49
 */
public interface DeviceCategoryService {
	/**
	 * 增
	 * @param deviceCategory
	 * @return
	 */
	boolean insert(DeviceCategory deviceCategory);

	/**
	 * 改
	 * @param deviceCategory
	 * @return
	 */
	boolean update(DeviceCategory deviceCategory);

	/**
	 * 删
	 * @param id
	 * @return
	 */
	boolean deleted(long id);

	/**
	 * 查询所有的设备分类
	 * @return
	 */
	List<DeviceCategory> listAll();

	/**
	 * 获取所有设备类别ID的子列表
	 * @return List<Map<String,Object>>
	 * @author 招黄轩
	 */
	List<Map<String,Object>> categoryIdsSonList(Long pid);
}
