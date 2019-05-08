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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
		deviceModel.setDatetime(LocalDateTime.now());
		deviceModel.setDeleted(false);

		return deviceModelMapper.insert(deviceModel) > 0 ? true : false;
	}

	@Override
	public boolean insertSonDeviceModel(DeviceModel deviceModel, long pid, Integer amount) {
		deviceModel.setIsSlave(true);
		deviceModel.setDatetime(LocalDateTime.now());
		deviceModel.setDeleted(false);
		long slaveId = deviceModelMapper.insert(deviceModel);

		DeviceModelSlaves deviceModelSlaves = new DeviceModelSlaves();
		deviceModelSlaves.setAmount(amount);
		deviceModelSlaves.setDatetime(LocalDateTime.now());
		deviceModelSlaves.setMaterId(pid);
		deviceModelSlaves.setSlaveId(slaveId);

		return deviceModelSlavesMapper.insert(deviceModelSlaves) > 0 ? true : false;
	}

	@Override
	public boolean update(DeviceModel deviceModel) {
		DeviceModel old_deviceModel = deviceModelMapper.selectByPrimaryKey(deviceModel.getId());
		old_deviceModel.setId(null);
		deviceModelMapper.insert(old_deviceModel);

		deviceModel.setDatetime(LocalDateTime.now());
		return deviceModelMapper.updateByPrimaryKey(deviceModel) > 0 ? true : false;
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
		return deviceModelMapper.selectByPid(id);
	}
}
