package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.DeviceModel;
import edu.uni.labManagement.bean.DeviceModelSlaves;
import edu.uni.labManagement.mapper.DeviceModelMapper;
import edu.uni.labManagement.mapper.DeviceModelSlavesMapper;
import edu.uni.labManagement.service.DeviceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
	public long insertSlaveDeviceModel(DeviceModel deviceModel) {
		if(deviceModel.getIsSlave() == null) {
			deviceModel.setIsSlave(false);
		}
		long id = deviceModelMapper.insert(deviceModel);
		return id;
	}

	@Override
	public long insertSonDeviceModel(DeviceModel deviceModel, long pid, int amount, long userid, long universityid) {
		if(deviceModel.getIsSlave() == null) {
			deviceModel.setIsSlave(false);
		}
		long id = deviceModelMapper.insert(deviceModel);


		DeviceModelSlaves deviceModelSlaves = new DeviceModelSlaves();
		deviceModelSlaves.setAmount(amount);
		deviceModelSlaves.setByWho(userid);
		deviceModelSlaves.setDatetime(LocalDateTime.now());
		deviceModelSlaves.setUniversityId(universityid);
		deviceModelSlaves.setMaterId(pid);

		deviceModelSlavesMapper.insert(deviceModelSlaves);

		return id;
	}
}
