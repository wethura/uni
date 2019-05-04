package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.DeviceCategory;
import edu.uni.labManagement.bean.DeviceCategoryExample;
import edu.uni.labManagement.mapper.DeviceCategoryMapper;
import edu.uni.labManagement.service.DeviceCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/04/30 12:49
 */

@Service
public class DeviceCategoryServiceImpl implements DeviceCategoryService {

	@Resource
	private DeviceCategoryMapper deviceCategoryMapper;

	@Override
	public boolean insert(DeviceCategory deviceCategory) {
		deviceCategory.setDatetime(LocalDateTime.now());
		return deviceCategoryMapper.insert(deviceCategory) > 0 ? true : false;
	}

	@Override
	public boolean update(DeviceCategory deviceCategory) {
		return deviceCategoryMapper.updateByPrimaryKey(deviceCategory) > 0 ? true :false;
	}

	@Override
	public boolean deleted(long id) {
		return deviceCategoryMapper.deleteByPrimaryKey(id) > 0 ? true : false;
	}

	@Override
	public List<DeviceCategory> listAll() {
		DeviceCategoryExample example = new DeviceCategoryExample();
		DeviceCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(true);
		List<DeviceCategory> deviceCategoryList = deviceCategoryMapper.selectByExample(example);

		return deviceCategoryList;
	}

}
