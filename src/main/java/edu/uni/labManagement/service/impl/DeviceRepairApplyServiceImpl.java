package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.DeviceRepairApply;
import edu.uni.labManagement.bean.DeviceRepairApplyExample;
import edu.uni.labManagement.mapper.DeviceRepairApplyMapper;
import edu.uni.labManagement.service.DeviceRepairApplyService;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

	@Override
	public boolean insert(DeviceRepairApply deviceRepairApply) {
		deviceRepairApply.setDatetime(LocalDateTime.now());
		deviceRepairApply.setApplyTime(LocalDateTime.now());
		return deviceRepairApplyMapper.insert(deviceRepairApply) > 0 ? true : false;
	}

	@Override
	public boolean update(DeviceRepairApply deviceRepairApply) {
		deviceRepairApply.setReviewTime(LocalDateTime.now());
		return deviceRepairApplyMapper.updateByPrimaryKeySelective(deviceRepairApply) > 0 ? true : false;
	}

	@Override
	public boolean deleted(long id) {
		return deviceRepairApplyMapper.deleteByPrimaryKey(id) > 0 ? true : false;
	}

	@Override
	public List<DeviceRepairApply> listByStates(int states) {
		DeviceRepairApplyExample example = new DeviceRepairApplyExample();
		DeviceRepairApplyExample.Criteria criteria = example.createCriteria();
		criteria.andIsSuccessEqualTo(states);
		criteria.andDeletedEqualTo(true);
		return deviceRepairApplyMapper.selectByExample(example);
	}

	@Override
	public List<DeviceRepairApply> listByDeviceId(long id) {
		DeviceRepairApplyExample example = new DeviceRepairApplyExample();
		DeviceRepairApplyExample.Criteria criteria = example.createCriteria();
		criteria.andDeviceIdEqualTo(id);
		criteria.andDeletedEqualTo(true);
		return deviceRepairApplyMapper.selectByExample(example);
	}

	@Override
	public DeviceRepairApply listById(long id) {
		return deviceRepairApplyMapper.selectByPrimaryKey(id);
	}
}
