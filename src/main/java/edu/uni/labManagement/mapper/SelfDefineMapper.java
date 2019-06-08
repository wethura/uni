package edu.uni.labManagement.mapper;

import edu.uni.labManagement.bean.DeviceCategory;

import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/03 21:52
 */
public interface SelfDefineMapper {

	/**
	 * 通过部门id查部门名称
	 * @param name
	 * @return
	 */
	Long selectDepartmentIdByName(String name);

	/**
	 * 通过场地名称查场地id
	 * @param name
	 * @return
	 */
	Long selectFieldIdByName(String name);

	/**
	 * 通过用户账户查用户id
	 * @param account
	 * @return
	 */
	Long selectUserIdByAccount(String account);

	/**
	 * 通过分类名称查分类id
	 * @param pid
	 * @param name
	 * @return
	 */
	Long selectCategoryIdByName(Long pid, String name);

	/**
	 * 通过长度id查场地名称
	 * @param id
	 * @return
	 */
	String selectFieldNameById(Long id);

	/**
	 * 通过部门id查部门名称
	 * @param id
	 * @return
	 */
	String selectDepartmentNameById(Long id);

	/**
	 * 通过设备id查询所在实验室
	 * @param deviceId
	 * @return
	 */
	String selectLabNameByDeviceId(Long deviceId);

	/**
	 * 通过子设备分类id查询父设备分类
	 */
//	DeviceCategory selectCategoryBtSonId(Long sonId);

	List<Map<String, Object>> listallDepartment();
}
