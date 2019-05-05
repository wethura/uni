package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.bean.DeviceExample;
import edu.uni.labManagement.mapper.DeviceMapper;
import edu.uni.labManagement.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/05 11:23
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	@Resource
	private DeviceMapper deviceMapper;

	@Override
	public List<Device> listAll() {
		DeviceExample example = new DeviceExample();
		DeviceExample.Criteria criteria = example.createCriteria();
		criteria.andIsMasterEqualTo(true);
		criteria.andDeletedEqualTo(true);
		return deviceMapper.selectByExample(example);
	}
}
