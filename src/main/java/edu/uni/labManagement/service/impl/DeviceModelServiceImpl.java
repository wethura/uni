package edu.uni.labManagement.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import edu.uni.labManagement.bean.DeviceModel;
import edu.uni.labManagement.bean.DeviceModelExample;
import edu.uni.labManagement.bean.DeviceModelSlaves;
import edu.uni.labManagement.mapper.DeviceModelMapper;
import edu.uni.labManagement.mapper.DeviceModelSlavesMapper;
import edu.uni.labManagement.service.DeviceModelService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/30 1:50
 */
@Service
public class DeviceModelServiceImpl implements DeviceModelService {
	@Resource
	private DeviceModelMapper deviceModelMapper;
	@Resource
	private DeviceModelSlavesMapper deviceModelSlavesMapper;

	@Override
	public boolean insertParentDeviceModel(DeviceModel deviceModel) {
		deviceModel.setIsSlave(false);
		deviceModel.setDatetime(new Date());
		deviceModel.setDeleted(false);

		return deviceModelMapper.insert(deviceModel) > 0 ? true : false;
	}

	@Override
	public boolean insertSonDeviceModel(DeviceModel deviceModel, long pid, Integer amount) {
		deviceModel.setIsSlave(true);
		deviceModel.setDatetime(new Date());
		deviceModel.setDeleted(false);
		long slaveId = deviceModelMapper.insert(deviceModel);
System.out.println("------------------>" + deviceModel);
		DeviceModelSlaves deviceModelSlaves = new DeviceModelSlaves();
		deviceModelSlaves.setAmount(amount);
		deviceModelSlaves.setDatetime(new Date());
		deviceModelSlaves.setMaterId(pid);
		deviceModelSlaves.setSlaveId(deviceModel.getId());

		return deviceModelSlavesMapper.insert(deviceModelSlaves) > 0 ? true : false;
	}

	@Override
	public boolean update(DeviceModel deviceModel) {
		DeviceModel old_deviceModel = deviceModelMapper.selectByPrimaryKey(deviceModel.getId());
		old_deviceModel.setId(null);
		old_deviceModel.setDeleted(true);
		deviceModelMapper.insert(old_deviceModel);

		deviceModel.setDatetime(new Date());
		return deviceModelMapper.updateByPrimaryKeySelective(deviceModel) > 0 ? true : false;
	}

	@Override
	public boolean deleted(long id) {
		DeviceModel deviceModel = new DeviceModel();
		deviceModel.setId(id);
		deviceModel.setDeleted(true);
		return deviceModelMapper.updateByPrimaryKeySelective(deviceModel) > 0 ? true : false;
	}

	@Override
	public List<DeviceModel> listByPid(long id) {
//		自定义mapper
		return deviceModelMapper.selectByPid(id);
	}

	@Override
	public List<DeviceModel> listByCategoryId(long id) {
		DeviceModelExample example = new DeviceModelExample();
		DeviceModelExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		criteria.andIsSlaveEqualTo(false);
		criteria.andDeviceCategoryIdEqualTo(id);
		return deviceModelMapper.selectByExample(example);
	}
}
