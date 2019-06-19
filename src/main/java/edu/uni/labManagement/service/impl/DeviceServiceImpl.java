package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.bean.DeviceExample;
import edu.uni.labManagement.bean.LabDevice;
import edu.uni.labManagement.bean.LabDeviceExample;
import edu.uni.labManagement.config.LabManagementConfig;
import edu.uni.labManagement.mapper.DeviceMapper;
import edu.uni.labManagement.mapper.LabDeviceMapper;
import edu.uni.labManagement.mapper.SelfDefineMapper;
import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.service.DeviceService;
import edu.uni.labManagement.service.ExcelDataIO;
import edu.uni.labManagement.service.SelfDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
	@Resource
	private SelfDefineMapper selfDefineMapper;
	@Resource
	private LabDeviceMapper labDeviceMapper;
	@Autowired
	private LabManagementConfig golbalconfig;
	@Autowired
	private ExcelDataIO excelDataIO;
	@Autowired
	private AuthService authService;

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

		pojo.setSerial(device.getSerialNumber());
		pojo.setModel(device.getModel());
		pojo.setProductDate(device.getProductDate().toString());
		pojo.setCategory(String.join("、", excelDataIO.findCategoryFull(device.getDeviceCategoryId())));
		pojo.setDescription(device.getDescription());

		pojo.setDepartment(selfDefineMapper.selectDepartmentNameById(device.getDepartmentId()));
		pojo.setLabName(selfDefineMapper.selectLabNameByDeviceId(device.getId()));
		pojo.setNumber(device.getNumber());
		pojo.setName(device.getName());
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

	@Override
	public boolean deletedLabDevice(Long labId, Long deviceId) {
		LabDeviceExample example = new LabDeviceExample();
		LabDeviceExample.Criteria criteria = example.createCriteria();
		criteria.andLabIdEqualTo(labId)
				.andDeviceIdEqualTo(deviceId);
		LabDevice labDevice = new LabDevice();
		labDevice.setDeleted(true);
		return labDeviceMapper.updateByExampleSelective(labDevice, example) > 0 ? true : false;
	}

	@Override
	public boolean updateDevice(Long id, String number, Long labId) {

		User user = authService.getUser();

		Device device = deviceMapper.selectByPrimaryKey(id);
		device.setNumber(number);
		LabDevice labDevice = new LabDevice();
		labDevice.setLabId(labId);
		labDevice.setDeleted(false);
		labDevice.setByWho(user.getId());
		labDevice.setUniversityId(user.getUniversityId());
		labDevice.setDatetime(new Date());
		labDevice.setDeviceId(id);
		labDeviceMapper.insert(labDevice);
		return deviceMapper.updateByPrimaryKeySelective(device) > 0;
	}
}
