package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.DeviceCategory;
import edu.uni.labManagement.bean.DeviceCategoryExample;
import edu.uni.labManagement.mapper.DeviceCategoryMapper;
import edu.uni.labManagement.service.DeviceCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
		deviceCategory.setDatetime(new Date());
		deviceCategory.setDeleted(false);
		return deviceCategoryMapper.insert(deviceCategory) > 0 ? true : false;
	}

	@Override
	public boolean update(DeviceCategory deviceCategory) {

		DeviceCategory old_deviceCategory = deviceCategoryMapper.selectByPrimaryKey(deviceCategory.getId());
		old_deviceCategory.setId(null);
		old_deviceCategory.setDeleted(true);
		deviceCategoryMapper.insert(old_deviceCategory);

		deviceCategory.setDatetime(new Date());
		return deviceCategoryMapper.updateByPrimaryKeySelective(deviceCategory) > 0 ? true :false;
	}

	@Override
	public boolean deleted(long id) {
		DeviceCategory deviceCategory = new DeviceCategory();
		deviceCategory.setId(id);
		deviceCategory.setDeleted(true);
		return deviceCategoryMapper.updateByPrimaryKeySelective(deviceCategory) > 0 ? true : false;
	}

	@Override
	public List<DeviceCategory> listAll() {
		DeviceCategoryExample example = new DeviceCategoryExample();
		DeviceCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		List<DeviceCategory> deviceCategoryList = deviceCategoryMapper.selectByExample(example);

		return deviceCategoryList;
	}

	@Override
	public List<Map<String,Object>> categoryIdsSonList(Long pid) {
		DeviceCategoryExample example = new DeviceCategoryExample();
		DeviceCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if(pid == null) criteria.andPidIsNull();
		else criteria.andPidEqualTo(pid);
		List<DeviceCategory> list = deviceCategoryMapper.selectByExample(example);

		List<Map<String,Object>> res = new LinkedList<>();
		for(DeviceCategory deviceCategory : list) {
			Map<String,Object> prop = new HashMap<>();
			prop.put("value", deviceCategory.getId());
			prop.put("label", deviceCategory.getName());
			prop.put("children", categoryIdsSonList(deviceCategory.getId()));
			res.add(prop);
		}
		return res;
	}
}
