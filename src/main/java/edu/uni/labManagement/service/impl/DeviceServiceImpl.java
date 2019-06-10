package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.example.config.ExampleConfig;
import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.bean.DeviceExample;
import edu.uni.labManagement.mapper.DeviceMapper;
import edu.uni.labManagement.mapper.SelfDefineMapper;
import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.service.DeviceService;
import edu.uni.labManagement.service.ExcelDataIO;
import edu.uni.labManagement.service.SelfDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	@Autowired
	private ExcelDataIO excelDataIO;
	@Autowired
	private SelfDefineMapper selfDefineMapper;

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
	public Device selectById(Long deviceId) {
		return deviceMapper.selectByPrimaryKey(deviceId);
	}

	@Override
	public ExcelDevicePojo selectPojoById(Long deviceId) {

		Device device = deviceMapper.selectByPrimaryKey(deviceId);
		ExcelDevicePojo pojo = new ExcelDevicePojo();
		pojo.setId(String.valueOf(device.getId()));
		pojo.setSerial(device.getSerialNumber());
		pojo.setModel(device.getModel());
		pojo.setProductDate(device.getProductDate().toString());
		pojo.setCategory(String.join("、", excelDataIO.findCategoryFull(device.getDeviceCategoryId())));
		pojo.setDescription(device.getDescription());
		pojo.setDepartment(selfDefineMapper.selectDepartmentNameById(device.getDepartmentId()));
		pojo.setLabName(selfDefineMapper.selectLabNameByDeviceId(device.getId()));
		pojo.setNumber(device.getNumber());
		pojo.setBatch(String.valueOf(device.getBatch()));

		return pojo;
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

	/**
	 * 查询所有设备的id和name
	 * @return List<Map<String,Object>>
	 */
	@Override
	public List<Map<String,Object>> selectByTwo(){
		return deviceMapper.selectByTwo();
	}
}
