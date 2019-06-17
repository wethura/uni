package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.DeviceRepairApply;
import edu.uni.labManagement.bean.DeviceRepairApplyExample;
import edu.uni.labManagement.bean.MaintenanceRecords;
import edu.uni.labManagement.config.LabManagementConfig;
import edu.uni.labManagement.mapper.DeviceMapper;
import edu.uni.labManagement.mapper.DeviceRepairApplyMapper;
import edu.uni.labManagement.mapper.MaintenanceRecordsMapper;
import edu.uni.labManagement.pojo.DeviceRepairApplyPojo;
import edu.uni.labManagement.service.DeviceRepairApplyService;
import edu.uni.labManagement.service.MaintenanceRecordsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/03 23:51
 */
@Service
public class DeviceRepairApplyServiceImpl implements DeviceRepairApplyService {

	@Resource
	private DeviceRepairApplyMapper deviceRepairApplyMapper;
	@Autowired
	private LabManagementConfig golbalConfig;
	@Resource
	private DeviceMapper deviceMapper;
	@Resource
	private MaintenanceRecordsMapper maintenanceRecordsMapper;
	@Autowired
	private AuthService authService;

	@Override
	public boolean insert(DeviceRepairApply deviceRepairApply) {
		deviceRepairApply.setDatetime(new Date());
		deviceRepairApply.setApplyTime(new Date());
		deviceRepairApply.setDeleted(false);
		deviceRepairApply.setIsSuccess(0);
		deviceRepairApply.setByWho(((User)authService.getUser()).getId());
		deviceRepairApply.setUniversityId(((User)authService.getUser()).getUniversityId());
		deviceRepairApply.setUserId(((User)authService.getUser()).getId());

		return deviceRepairApplyMapper.insert(deviceRepairApply) > 0 ? true : false;
	}

	@Override
	public boolean update(DeviceRepairApply deviceRepairApply) {
		deviceRepairApply.setReviewTime(new Date());
		deviceRepairApply.setAssersorId(((User)authService.getUser()).getId());
		deviceRepairApply.setUniversityId(((User)authService.getUser()).getUniversityId());
		return deviceRepairApplyMapper.updateByPrimaryKeySelective(deviceRepairApply) > 0 ? true : false;
	}

	@Override
	public boolean deleted(long id) {
		DeviceRepairApply apply = new DeviceRepairApply();
		apply.setId(id);
		apply.setDeleted(true);
		return deviceRepairApplyMapper.updateByPrimaryKeySelective(apply) > 0 ? true : false;
	}

	@Override
	public PageInfo<DeviceRepairApplyPojo> listByStates(int states, int pageNum) {
		PageHelper.startPage(pageNum, golbalConfig.getPageSize());

		DeviceRepairApplyExample example = new DeviceRepairApplyExample();
		DeviceRepairApplyExample.Criteria criteria = example.createCriteria();
		criteria.andIsSuccessEqualTo(states)
				.andDeletedEqualTo(false);
		List<DeviceRepairApply> list = deviceRepairApplyMapper.selectByExample(example);

		List<DeviceRepairApplyPojo> lists = new ArrayList<>();
		for (DeviceRepairApply apply : list) {
			DeviceRepairApplyPojo pojo = new DeviceRepairApplyPojo();
			BeanUtils.copyProperties(apply, pojo);
			String deviceName = null, applyUser = null, reviewUser = null;

			if (pojo.getDeviceId() != null && pojo.getDeviceId() > 0) {
				pojo.setDeviceName(deviceMapper.selectByPrimaryKey(pojo.getId()).getName());
			}
			if (pojo.getUserId() != null && pojo.getUserId() > 0) {
				pojo.setApplyUser(maintenanceRecordsMapper.selectUserById(pojo.getUserId()));
				System.out.println(maintenanceRecordsMapper.selectUserById(pojo.getUserId()));
			}
			if (pojo.getAssersorId() != null && pojo.getAssersorId() > 0) {
				pojo.setReviewUser(maintenanceRecordsMapper.selectUserById(pojo.getAssersorId()));
				System.out.println(maintenanceRecordsMapper.selectUserById(pojo.getAssersorId()));
			}
			lists.add(pojo);
		}
		if (lists != null) {
			return new PageInfo<>(lists);
		} else {
			return null;
		}
	}

	@Override
	public List<DeviceRepairApply> listByDeviceId(long id) {
		DeviceRepairApplyExample example = new DeviceRepairApplyExample();
		DeviceRepairApplyExample.Criteria criteria = example.createCriteria();
		criteria.andDeviceIdEqualTo(id);
		criteria.andDeletedEqualTo(false);
		return deviceRepairApplyMapper.selectByExample(example);
	}

	@Override
	public DeviceRepairApply listById(long id) {
		return deviceRepairApplyMapper.selectByPrimaryKey(id);
	}
}
