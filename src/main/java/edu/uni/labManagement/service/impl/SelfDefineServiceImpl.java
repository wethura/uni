package edu.uni.labManagement.service.impl;

import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.LabAdmin;
import edu.uni.labManagement.bean.LabAdminExample;
import edu.uni.labManagement.mapper.LabAdminMapper;
import edu.uni.labManagement.mapper.LabMapper;
import edu.uni.labManagement.mapper.SelfDefineMapper;
import edu.uni.labManagement.mapper.UserMapper;
import edu.uni.labManagement.service.SelfDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Resource
	private UserMapper userMapper;
	@Autowired
	private AuthService authService;

	@Override
	public List<Map<String, Object>> listAllDepartment() {
		User user = authService.getUser();
		return selfDefineMapper.listallDepartment(user.getUniversityId());
	}

	@Override
	public boolean insertAdmins(List<String> admins, long labId) throws Exception {
		for (String admin : admins) {
			System.out.println(admin + " <------- ");
			Long adminId = selfDefineMapper.selectUserIdByAccount(admin);

			if (adminId == null) throw new Exception("管理员不存在！");

			LabAdmin labAdmin = new LabAdmin();
			labAdmin.setDeleted(false);
			labAdmin.setDatetime(new Date());
			labAdmin.setLabId(labId);
			labAdmin.setAdminId(adminId);

			labAdminMapper.insert(labAdmin);
		}
		return true;
	}

	@Override
	public boolean updateAdmins(List<String> admins, Long labId) throws Exception {
		User user = authService.getUser();

		LabAdminExample example = new LabAdminExample();
		LabAdminExample.Criteria criteria = example.createCriteria();
		criteria.andLabIdEqualTo(labId)
				.andDeletedEqualTo(false)
				.andUniversityIdEqualTo(user.getUniversityId());
		LabAdmin labAdminNew = new LabAdmin();
		labAdminNew.setDeleted(true);
		labAdminMapper.updateByExampleSelective(labAdminNew, example);

		LabAdmin labAdmin = new LabAdmin();
		for (String admin: admins){
			labAdmin.setUniversityId(authService.getUser().getUniversityId());
			labAdmin.setDatetime(new Date());
			Long userId = selfDefineMapper.selectUserIdByAccount(admin);
			labAdmin.setAdminId(userId);
			labAdmin.setDeleted(false);
			labAdmin.setByWho(authService.getUser().getId());
			labAdmin.setLabId(labId);

			labAdminMapper.insert(labAdmin);
		}

		return insertAdmins(admins, labId);
	}
}
