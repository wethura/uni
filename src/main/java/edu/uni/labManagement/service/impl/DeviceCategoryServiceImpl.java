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
	public long insert(DeviceCategory deviceCategory) {
		deviceCategory.setDatetime(LocalDateTime.now());
		long id = deviceCategoryMapper.insert(deviceCategory);
		return id;
	}

	@Override
	public void update(DeviceCategory deviceCategory) {
		deviceCategoryMapper.updateByPrimaryKey(deviceCategory);
	}

	@Override
	public List<DeviceCategory> listAll() {
		DeviceCategoryExample example = new DeviceCategoryExample();
		DeviceCategoryExample.Criteria criteria = example.createCriteria();
		List<DeviceCategory> deviceCategoryList = deviceCategoryMapper.selectByExample(example);

//		洗去无效数据
		for(int i = deviceCategoryList.size() - 1; i >= 0; i --){
			if(deviceCategoryList.get(i).getDeleted().equals(false)){
				deviceCategoryList.remove(i);
			}
		}
		return deviceCategoryList;
	}

}
