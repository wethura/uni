package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.DeviceModel;
import edu.uni.labManagement.mapper.DeviceModelMapper;
import edu.uni.labManagement.service.DeviceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
	private DeviceModel
	@Override
	public long insertSlaveDeviceModel(DeviceModel deviceModel) {
		if(deviceModel.getIsSlave() == null) {
			deviceModel.setIsSlave(false);
		}
		int id = deviceModelMapper.insert(deviceModel);
		return id;
	}

	@Override
	public long insertParentDeviceModel(DeviceModel deviceModel, long pid) {
		if(deviceModel.getIsSlave() == null) {
			deviceModel.setIsSlave(false);
		}
		int id = deviceModelMapper.insert(deviceModel);


		return id;
	}
}
