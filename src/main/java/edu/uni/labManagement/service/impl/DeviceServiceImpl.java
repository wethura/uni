package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.example.config.ExampleConfig;
import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.bean.DeviceExample;
import edu.uni.labManagement.mapper.DeviceMapper;
import edu.uni.labManagement.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private ExampleConfig golbalconfig;

	@Override
	public PageInfo<Device> listAll(int pageNum) {
		PageHelper.startPage(pageNum, golbalconfig.getPageSize());

		DeviceExample example = new DeviceExample();
		DeviceExample.Criteria criteria = example.createCriteria();
		criteria.andIsMasterEqualTo(true);
		criteria.andDeletedEqualTo(false);
		List<Device> devices = deviceMapper.selectByExample(example);
		if (devices != null) {
			return new PageInfo<>(devices);
		} else {
			return null;
		}
	}

	@Override
	public List<Device> listAllByLabId(long labId) {
		return deviceMapper.selectByLabId(labId);
	}

	@Override
	public List<Device> selectByParentId(long id) {
		return deviceMapper.selectByParentId(id);
	}

	@Override
	public List<String> selectDistinctDeviceName(long labId) {
		return deviceMapper.selectDistinctDeviceName(labId);
	}

	@Override
	public List<Device> listByNameAndLab(String name, long labId) {
		List<Device> list = deviceMapper.selectByLabId(labId);
		for (int i = list.size() - 1; i >= 0; i --) {
			if (!name.equals(list.get(i).getName())) {
				list.remove(i);
			}
		}
		return list;
	}
}
