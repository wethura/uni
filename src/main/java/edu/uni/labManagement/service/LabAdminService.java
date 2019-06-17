package edu.uni.labManagement.service;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/16 20:35
 */
public interface LabAdminService {
	/**
	 * 直接更新所有的管理员为最新的管理员
	 * @param adminsStr
	 * @param labId
	 * @return
	 */
	boolean updateLabAdmin(String adminsStr, Long labId);
}
