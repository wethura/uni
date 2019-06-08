package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.LabAdmin;
import edu.uni.labManagement.mapper.LabAdminMapper;
import edu.uni.labManagement.mapper.LabMapper;
import edu.uni.labManagement.mapper.SelfDefineMapper;
import edu.uni.labManagement.service.SelfDefineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/08 16:16
 */
@Service
public class SelfDefineServiceImpl implements SelfDefineService {

	@Resource
	private SelfDefineMapper selfDefineMapper;
	@Resource
	private LabAdminMapper labAdminMapper;

	@Override
	public List<Map<String, Object>> listAllDepartment() {
		return selfDefineMapper.listallDepartment();
	}

	@Override
	public boolean insertAdmins(String[] admins, long labId) {
		try {
			for (String admin : admins){
				Long amdinId = selfDefineMapper.selectUserIdByAccount(admin);

				LabAdmin labAdmin = new LabAdmin();
				labAdmin.setDeleted(false);
				labAdmin.setDatetime(new Date());
				labAdmin.setLabId(labId);

				labAdminMapper.insert(labAdmin);
			}
		} catch (Exception e){
			return false;
		}
		return true;
	}
}
