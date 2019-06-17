package edu.uni.labManagement.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.*;
import edu.uni.labManagement.mapper.*;
import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.service.DeviceCategoryService;
import edu.uni.labManagement.service.DeviceModelService;
import edu.uni.labManagement.service.ExcelDataIO;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
	@Resource
	private DeviceCategoryMapper deviceCategoryMapper;
	@Resource
	private LabMapper labMapper;
	@Resource
	private PFieldMapper pFieldMapper;
	@Resource
	private PSchoolAreaMapper pSchoolAreaMapper;
	@Resource
	private PSchoolMapper pSchoolMapper;
	@Resource
	private DepartmentMapper departmentMapper;
	@Autowired
	private DeviceCategoryService deviceCategoryService;
	@Autowired
	private ExcelDataIO excelDataIO;
	@Autowired
	private AuthService authService;

	@Override
	public boolean insertParentDeviceModel(DeviceModel deviceModel) {
		deviceModel.setIsSlave(false);
		deviceModel.setDatetime(new Date());
		deviceModel.setDeleted(false);
		deviceModel.setUniversityId(((User)authService.getUser()).getUniversityId());
		deviceModel.setByWho(((User)authService.getUser()).getId());

		return deviceModelMapper.insert(deviceModel) > 0 ? true : false;
	}

	@Override
	public boolean insertSonDeviceModel(DeviceModel deviceModel, long pid, Integer amount) {
		deviceModel.setIsSlave(true);
		deviceModel.setDatetime(new Date());
		deviceModel.setDeleted(false);
		deviceModel.setUniversityId(((User)authService.getUser()).getUniversityId());
		deviceModel.setByWho(((User)authService.getUser()).getId());

		long slaveId = deviceModelMapper.insert(deviceModel);
		DeviceModelSlaves deviceModelSlaves = new DeviceModelSlaves();
		deviceModelSlaves.setAmount(amount);
		deviceModelSlaves.setDatetime(new Date());
		deviceModelSlaves.setMaterId(pid);
		deviceModelSlaves.setSlaveId(deviceModel.getId());
		deviceModelSlaves.setUniversityId(deviceModel.getUniversityId());
		deviceModelSlaves.setByWho(deviceModel.getByWho());

		return deviceModelSlavesMapper.insert(deviceModelSlaves) > 0 ? true : false;
	}

	@Override
	public boolean update(DeviceModel deviceModel) {
		DeviceModel old_deviceModel = deviceModelMapper.selectByPrimaryKey(deviceModel.getId());
		old_deviceModel.setId(null);
		old_deviceModel.setDeleted(true);
		deviceModelMapper.insert(old_deviceModel);

		deviceModel.setDatetime(new Date());
		deviceModel.setUniversityId(((User)authService.getUser()).getUniversityId());
		deviceModel.setByWho(((User)authService.getUser()).getId());
		return deviceModelMapper.updateByPrimaryKeySelective(deviceModel) > 0 ? true : false;
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
//		自定义mapper
		return deviceModelMapper.selectByPid(id);
	}

	@Override
	public List<DeviceModel> listByCategoryId(long id) {
		DeviceModelExample example = new DeviceModelExample();
		DeviceModelExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		criteria.andIsSlaveEqualTo(false);
		criteria.andDeviceCategoryIdEqualTo(id);
		return deviceModelMapper.selectByExample(example);
	}

	/**
	 * 查询所有设备型号的id和name
	 * @return List<Map<String,Object>>
	 */
	@Override
	public List<Map<String,Object>> selectByTwo(){
		return deviceModelMapper.selectByTwo();
	}

	/**
	 * 通过设备模型生成excelDevice封装类
	 * @param modelId
	 * @param labId
	 * @return
	 */
	@Override
	public ExcelDevicePojo selectDevicePojoByKey(Long modelId, Long labId) {

		Lab lab = labMapper.selectByPrimaryKey(labId);
		PField pField = pFieldMapper.selectByPrimaryKey(lab.getFieldId());
		PSchoolArea pSchoolArea = pSchoolAreaMapper.selectByPrimaryKey(pField.getAreaId());
		PSchool pSchool = pSchoolMapper.selectByPrimaryKey(pSchoolArea.getSchoolId());
		Department department = departmentMapper.selectByPrimaryKey(lab.getDepartmentId());


		DeviceModel deviceModel = deviceModelMapper.selectByPrimaryKey(modelId);
		ExcelDevicePojo excelDevicePojo = new ExcelDevicePojo();
		excelDevicePojo.setName(deviceModel.getName());
		excelDevicePojo.setCategory(String.join("、",excelDataIO.findCategoryFull(deviceModel.getDeviceCategoryId())));
		excelDevicePojo.setSchoolArea(pSchoolArea.getName());
		excelDevicePojo.setSchoolName(pSchool.getName());
		excelDevicePojo.setLabName(lab.getName());
		excelDevicePojo.setDescription(deviceModel.getDescription());
		excelDevicePojo.setProductDate(new Date().toString());
		excelDevicePojo.setDepartment(department.getName());
		excelDevicePojo.setModel(deviceModel.getVersion());

		return excelDevicePojo;
	}

	@Override
	public Map<String, Object> getFromDeviceModelId(Long deviceModelId) {
		Map<String, Object> mp = new HashMap<String, Object>();

		DeviceModel deviceModel = deviceModelMapper.selectByPrimaryKey(deviceModelId);
		mp.put("description", deviceModel.getDescription());
		mp.put("id", deviceModel.getId());
		mp.put("name", deviceModel.getName());
		mp.put("version", deviceModel.getVersion());
		mp.put("datetime", deviceModel.getDatetime());
		mp.put("producter", deviceModel.getProducter());
		mp.put("category", String.join("/", deviceCategoryService.categoryFullName(deviceModel.getDeviceCategoryId())));
		mp.put("categoryId", deviceModel.getDeviceCategoryId());

		return mp;

	}

	@Override
	public List<Map<String, Object>> getAll() {

		DeviceModelExample example = new DeviceModelExample();
		DeviceModelExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false)
				.andUniversityIdEqualTo(authService.getUser().getUniversityId());
		List<DeviceModel> deviceModels  = deviceModelMapper.selectByExample(example);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (DeviceModel deviceModel: deviceModels){
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("description", deviceModel.getDescription());
			mp.put("id", deviceModel.getId());
			mp.put("name", deviceModel.getName());
			mp.put("version", deviceModel.getVersion());
			mp.put("datetime", deviceModel.getDatetime());
			mp.put("producter", deviceModel.getProducter());
			mp.put("category", String.join("/", deviceCategoryService.categoryFullName(deviceModel.getDeviceCategoryId())));
			mp.put("categoryId", deviceModel.getDeviceCategoryId());

			list.add(mp);
		}
		return list;
	}
}
