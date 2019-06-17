package edu.uni.labManagement.service;

import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/08 16:12
 */
public interface SelfDefineService {

	List<Map<String, Object>> listAllDepartment();

	boolean insertAdmins(List<String> admins, long labId) throws Exception;

	boolean updateAdmins(List<String> admins, Long labId) throws Exception;
}
