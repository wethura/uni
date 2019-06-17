package edu.uni.labManagement.service.impl;

import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.LabAdmin;
import edu.uni.labManagement.bean.LabAdminExample;
import edu.uni.labManagement.bean.UserExample;
import edu.uni.labManagement.mapper.LabAdminMapper;
import edu.uni.labManagement.mapper.UserMapper;
import edu.uni.labManagement.service.LabAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/18 1:42
 */
@Service
public class LabAdminServiceImpl implements LabAdminService {

	@Resource
	private LabAdminMapper labAdminMapper;
	@Autowired
	private AuthService authService;
	@Resource
	private UserMapper userMapper;

	@Override
	public boolean updateLabAdmin(String adminsStr, Long labId){

		User user = authService.getUser();

		List<String> admins = Arrays.asList(adminsStr.split("、"));

//		删除原有的管理员
		LabAdminExample example = new LabAdminExample();
		LabAdminExample.Criteria criteria = example.createCriteria();
		criteria.andUniversityIdEqualTo(user.getUniversityId())
				.andLabIdEqualTo(labId)
				.andDeletedEqualTo(false);
		LabAdmin labAdmin = new LabAdmin();
		labAdmin.setDeleted(true);
		labAdminMapper.updateByExampleSelective(labAdmin, example);

//		增加新的管理员
		for (String admin: admins){

//			查找用户ID
			UserExample userExample = new UserExample();
			UserExample.Criteria userCriteria = userExample.createCriteria();
			userCriteria.andNameEqualTo(admin)
					.andStatusEqualTo(0);
			Long userId = userMapper.selectByExample(userExample).get(0).getId();

			LabAdmin labAdminNew = new LabAdmin();
			labAdminNew.setDeleted(false);
			labAdminNew.setByWho(user.getId());
			labAdminNew.setAdminId(userId);
			labAdminNew.setLabId(labId);
			labAdminNew.setUniversityId(user.getUniversityId());
			labAdminNew.setDatetime(new Date());
			labAdminMapper.insert(labAdminNew);
		}

		return true;
	}

}
