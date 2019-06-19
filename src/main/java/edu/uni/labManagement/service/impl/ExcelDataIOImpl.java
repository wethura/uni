package edu.uni.labManagement.service.impl;

import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.*;
import edu.uni.labManagement.mapper.*;
import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.pojo.ExcelLabPojo;
import edu.uni.labManagement.service.ExcelDataIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/03 20:55
 */
@Service
@Transactional(rollbackFor={RuntimeException.class,Exception.class})
public class ExcelDataIOImpl implements ExcelDataIO {

	@Resource
	private SelfDefineMapper selfDefineMapper;
	@Resource
	private LabMapper labMapper;
	@Resource
	private LabAdminMapper labAdminMapper;
	@Resource
	private DeviceMapper deviceMapper;
	@Resource
	private DeviceCategoryMapper deviceCategoryMapper;
	@Resource
	private DeviceModelMapper deviceModelMapper;
	@Resource
	private PSchoolMapper pSchoolMapper;
	@Resource
	private PSchoolAreaMapper pSchoolAreaMapper;
	@Resource
	private PFieldMapper pFieldMapper;
	@Resource
	private DepartmentMapper departmentMapper;
	@Resource
	private LabDeviceMapper labDeviceMapper;
	@Autowired
	private AuthService authService;

	/**
	 * Excel 数据转换、（V2.0）
	 * @param os
	 * @return
	 * @throws Exception
	 */
	@Override
	public String ExcelLabPojoImport(List<Object> os) throws Exception {
		ExcelLabPojo pojo = null;

		List<ExcelLabPojo> list = new ArrayList<ExcelLabPojo>();
		edu.uni.auth.bean.User user = (edu.uni.auth.bean.User) authService.getUser();
		if (user == null || user.getId() == null) throw new Exception("账户未登录！");


//      标题头检查
		if (os != null && os.size() > 0) {
			pojo = (ExcelLabPojo) os.get(0);
			System.out.println(pojo);
			if (!pojo.getName().startsWith("实验室名称") ||
					!pojo.getAddress().startsWith("地点名称") ||
					!pojo.getAdmins().startsWith("主管账号") ||
					!pojo.getDepartment().startsWith("行政部门") ||
					!pojo.getDescription().startsWith("实验室描述") ||
					!pojo.getPhone().startsWith("实验室电话") ||
					!pojo.getEname().startsWith("实验室英文名称") ||
					!pojo.getSchool().startsWith("校区") ||
					!pojo.getSchooolArea().startsWith("功能区")) {
				throw new Exception("您的数据标题对应不上，请标题栏顺序按照：实验室名称、实验室英文名称、实验室描述、实验室电话、主管账号、校区、行政部门、功能区、地点名称\n");
			}
		}

//		System.out.println(os.size());
//		一边数据检查一边插入数据，失败就抛出异常进行事务回滚
		for(int i = 1; i < os.size(); i ++) {
			pojo = (ExcelLabPojo) os.get(i);
			Lab lab = new Lab();

			Long schoolId = selfDefineMapper.selectSchoolIdByName(pojo.getSchool(), user.getUniversityId());
			if (schoolId == null || schoolId < 0) {
				throw new Exception("第" + (i + 1) + "行的 《学校》 不存在，请注意名称的正确性！");
			}

			Long schoolAreaId = selfDefineMapper.selectSchoolAreaIdByName(pojo.getSchooolArea(), schoolId);
			if (schoolAreaId == null || schoolAreaId < 0){
				throw new Exception("第" + (i + 1) + "行的 《功能区》 不存在，请注意名称的正确性！");
			}
			Long departmentId = selfDefineMapper.selectDepartmentIdByName(pojo.getDepartment(), schoolId);
			if (departmentId == null|| departmentId <= 0 ){
				throw new Exception("第" + (i + 1) + "行的 《部门》 不存在，请注意名称的正确性！");
			}
			Long filedId = selfDefineMapper.selectFieldIdByName(pojo.getAddress(), schoolAreaId);
			if(filedId == null ||filedId <= 0 ) {
				throw new Exception("第" + (i + 1) + "行的 《地点》 不存在，请注意名称的正确性！");
			}


//			实验室
			lab.setEname(pojo.getEname());
			lab.setDepartmentId(departmentId);
			lab.setName(pojo.getName());
			lab.setDescription(pojo.getDescription());
			lab.setFieldId(filedId);
			lab.setPhone(pojo.getPhone());

			lab.setDeleted(false);
			lab.setDatetime(new Date());
			lab.setByWho(user.getId());
			lab.setUniversityId(user.getUniversityId());

			Long labId = Long.valueOf(labMapper.insert(lab));
			if (labId == null || labId <= 0) {
				throw new Exception("实验室插入失败！请重试...");
			}

//			多个实验室管理员
			List<String> admins = Arrays.asList(pojo.getAdmins().split("、"));
			for (String admin: admins){
				LabAdmin labAdmin = new LabAdmin();
				Long adminId = selfDefineMapper.selectUserIdByAccount(admin);
				if (adminId == null ||adminId <= 0) {
					throw new Exception("第" + i + "行的《主管账户》存在问题！注意使用<、>符号分割主管账户！");
				}
				labAdmin.setAdminId(adminId);
				labAdmin.setLabId(lab.getId());
				labAdmin.setDeleted(false);
				labAdmin.setDatetime(new Date());
				labAdmin.setByWho(user.getId());
				labAdmin.setUniversityId(user.getUniversityId());
//              System.out.println(adminId + " <----> " + labId);

				int success = labAdminMapper.insert(labAdmin);
				if (success <= 0) {
					throw new Exception("实验室管理员插入导致失败，请重试...");
				}
			}
		}

		return "您的数据已经成功导入！";
	}

	/**
	 * 根据2019年6月4日修改需求后，暂时未将子设备给存进来。
	 * @param os
	 * @return
	 * @throws Exception
	 */
	@Override
	public String ExcelDevicePojoImport(List<Object> os) throws Exception {
		ExcelDevicePojo pojo = null;

		List<ExcelDevicePojo> list = new ArrayList<ExcelDevicePojo>();
		User user = authService.getUser();

//		标题头检查
		if (os != null && os.size() > 0) {
			pojo = (ExcelDevicePojo) os.get(0);
			System.out.println(pojo);
			if (    !pojo.getCategory().startsWith("设备类别") ||
					!pojo.getDepartment().startsWith("所属部门") ||
					!pojo.getDescription().startsWith("设备描述") ||
					!pojo.getModel().startsWith("设备型号") ||
					!pojo.getLabName().startsWith("实验室名称") ||
					!pojo.getName().startsWith("设备名称") ||
					!pojo.getProductDate().startsWith("设备生产日期") ||
					!pojo.getNumber().startsWith("设备编号") ||
					!pojo.getSerial().startsWith("序列号") ||
					!pojo.getSchoolArea().startsWith("功能区") ||
					!pojo.getSchoolName().startsWith("校区")){
				return "您的数据标题对应不上，请标题栏顺序按照：设备类别、设备名称、设备型号、设备编号、序列号、设备描述、设备生产日期、批次、校区、功能区、所属部门、实验室名称\n";
			}
		}

		System.out.println(os.size());
//		一边数据检查一边插入数据，失败就抛出异常进行事务回滚
		for (int i = 1; i < os.size(); i ++) {
			pojo = (ExcelDevicePojo) os.get(i);

			if (pojo.getSerial() == null || pojo.getNumber()==null || pojo.getProductDate()==null || pojo.getLabName() == null || pojo.getName() == null
			|| pojo.getDepartment() == null || pojo.getBatch() == null || pojo.getSerial() == "" || pojo.getNumber()=="" || pojo.getProductDate()=="" || pojo.getLabName() == "" || pojo.getName() == ""
					|| pojo.getDepartment() == "" || pojo.getBatch() == ""){continue;}
			System.out.println(pojo);

			DepartmentExample example = new DepartmentExample();
			DepartmentExample.Criteria criteria = example.createCriteria();
			criteria.andUniversityIdEqualTo(user.getUniversityId())
					.andDeletedEqualTo(false)
					.andNameEqualTo(pojo.getDepartment());
			Department department = departmentMapper.selectByExample(example).get(0);

			PSchoolExample example1 = new PSchoolExample();
			PSchoolExample.Criteria criteria1 = example1.createCriteria();
			criteria1.andNameEqualTo(pojo.getSchoolName())
					.andDeletedEqualTo(0)
					.andUniversityIdEqualTo(user.getUniversityId());
			PSchool pSchool = null;
			try {
				pSchool = pSchoolMapper.selectByExample(example1).get(0);
			}catch (Exception e){
				throw new Exception("学校存在错误！");
			}

			PSchoolAreaExample example2 = new PSchoolAreaExample();
			PSchoolAreaExample.Criteria criteria2 = example2.createCriteria();
			criteria2.andDeletedEqualTo(0)
					.andNameEqualTo(pojo.getSchoolArea())
					.andSchoolIdEqualTo(pSchool.getId())
					.andUniversityIdEqualTo(user.getUniversityId());
			PSchoolArea pSchoolArea = null;
			try {
				pSchoolArea = pSchoolAreaMapper.selectByExample(example2).get(0);
			} catch (Exception e){
				throw new Exception("第" + (i + 1) + "行的功能区错误或者不存在！");
			}

//			PFieldExample example3 = new PFieldExample();
//			PFieldExample.Criteria criteria3 = example3.createCriteria();
//			criteria3.andAreaIdEqualTo(pSchoolArea.getId())
//					.andNameEqualTo(pojo.ge)
//					.andDeletedEqualTo(0)
//					.andUniversityIdEqualTo(user.getUniversityId());
//			PField pField = null;
//			try {
//				pField = pFieldMapper.selectByExample(example3).get(0);
//			}catch (Exception e){
//				throw new Exception("第" + (i + 1) + "行的地点存在错误！");
//			}


			LabExample example4 = new LabExample();
			LabExample.Criteria criteria4 = example4.createCriteria();
			criteria4.andUniversityIdEqualTo(user.getUniversityId())
					.andDeletedEqualTo(false)
					.andDepartmentIdEqualTo(department.getId())
					.andNameEqualTo(pojo.getLabName());
			Lab lab = null;
			try {
				lab = labMapper.selectByExample(example4).get(0);
			} catch (Exception e){
				throw new Exception("第" + (i + 1) + "行的实验室错误或者不存在！");
			}

//			查找设备类别
			Long categoryId = findCategoryId(pojo.getCategory());
//			查找设备部门
			Long departmentId = department.getId();
//			批次字符串转化
			Integer batch = null;
			try { batch = Integer.valueOf(pojo.getBatch()); } catch (NumberFormatException e){ throw new Exception("批次只能为整数");}
//			日期转换
			Date date = null;
			try { date = new Date(pojo.getProductDate()); }
			catch (Exception e){ throw new Exception("第" + (i + 1) + "行的《设备生产日期》无法正常转化，请注意格式！"); }
//			数据检查
			if (categoryId == null){ throw new Exception("第" + (i + 1) + "行的《设备类别》找不到对应的分类，请检查格式是以</>进行区分父子类别！");}
			if (departmentId == null) { throw new Exception("第" + (i + 1) + "行的《所属部门》找不到，请检查部门是否存在或者格式是否正确！");}
			if (batch == null) { throw new Exception("第" + (i + 1) + "行的《批次》数据无法正常转化，请注意格式！"); }
			if (date == null) { throw new Exception("第" + (i + 1) + "行的《设备生产日期》无法正常转化，请注意格式！");}

//			设备数据填充
			Device device = new Device();
			device.setBatch(batch);
			device.setDeleted(false);
			device.setDatetime(new Date());
			device.setDescription(pojo.getDescription());
			device.setDepartmentId(departmentId);
			device.setDeviceCategoryId(categoryId);
			device.setModel(pojo.getModel());
			device.setIsMaster(true);
			device.setName(pojo.getName());
			device.setProductDate(date);
			device.setNumber(pojo.getNumber());
			device.setStatus(0);
			device.setSerialNumber(pojo.getSerial());
			device.setUniversityId(user.getUniversityId());
			device.setByWho(user.getId());
			boolean success = deviceMapper.insert(device) > 0 ? true : false;

			LabDevice labDevice = new LabDevice();
			labDevice.setDeviceId(device.getId());
			labDevice.setLabId(lab.getId());
			labDevice.setDatetime(new Date());
			labDevice.setUniversityId(user.getUniversityId());
			labDevice.setByWho(user.getId());
			labDevice.setDeleted(false);

			success = success && labDeviceMapper.insert(labDevice) > 0;

			if (!success) { throw new Exception("数据导入失败！");}
		}

		return "您的数据已经成功导入！";
	}


	/**
	 * Excel 导出实验室 service （V2.0）
	 * @return
	 * @throws Exception
	 */
	@Override
	public	List<ExcelLabPojo> ExcelLabPojoExport() throws Exception {

		User user = authService.getUser();

		LabExample example = new LabExample();
		LabExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false)
				.andUniversityIdEqualTo(user.getUniversityId());
		List<Lab> list = labMapper.selectByExample(example);

		List<ExcelLabPojo> ret = new ArrayList<ExcelLabPojo>();

		for (Lab lab: list){

			ExcelLabPojo pojo = new ExcelLabPojo();

			PField pField = pFieldMapper.selectByPrimaryKey(lab.getFieldId());
			pojo.setAddress(pField.getName());

			PSchool pSchool = pSchoolMapper.selectByPrimaryKey(pField.getSchoolId());
			pojo.setSchool(pSchool.getName());

			PSchoolArea pSchoolArea = pSchoolAreaMapper.selectByPrimaryKey(pField.getAreaId());
			pojo.setSchooolArea(pSchoolArea.getName());
			pojo.setDepartment(selfDefineMapper.selectDepartmentNameById(lab.getDepartmentId()));
			pojo.setDescription(lab.getDescription());

			List<String> admins = labAdminMapper.selectByLabId(lab.getId());
			pojo.setAdmins(String.join("、", admins));

			pojo.setEname(lab.getEname());
			pojo.setName(lab.getName());
			pojo.setPhone(lab.getPhone());

			ret.add(pojo);
		}

		return ret;
	}

	@Override
	public List<ExcelDevicePojo> ExcelDevicePojoCreateDemoForLab(Long count, Long labId, Long modelId, Date date) {

		List<ExcelDevicePojo> list = new ArrayList<ExcelDevicePojo>();

		ExcelDevicePojo pojo = new ExcelDevicePojo();
		DeviceModel model = deviceModelMapper.selectByPrimaryKey(modelId);
		Lab lab = labMapper.selectByPrimaryKey(labId);

		String categoryName = String.join("、", findCategoryFull(model.getDeviceCategoryId()));

		pojo.setProductDate(date.toString());
		pojo.setDepartment(selfDefineMapper.selectDepartmentNameById(lab.getDepartmentId()));
		pojo.setLabName(lab.getName());
		pojo.setDescription(model.getDescription());
		pojo.setCategory(categoryName);
		pojo.setName(model.getName());
//		pojo.set

		return list;
	}

	/**
	 * 导出设备 service
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ExcelDevicePojo> ExcelDevicePojoExport(List<Device> list) throws Exception {

		List<ExcelDevicePojo> ret = new ArrayList<ExcelDevicePojo>();
		User user = authService.getUser();

		for (Device device: list){

			LabDeviceExample example = new LabDeviceExample();
			LabDeviceExample.Criteria criteria = example.createCriteria();
			criteria.andDeviceIdEqualTo(device.getId())
					.andDeletedEqualTo(false)
					.andUniversityIdEqualTo(user.getUniversityId());
			LabDevice labDevice = labDeviceMapper.selectByExample(example).get(0);
			Lab lab = labMapper.selectByPrimaryKey(labDevice.getLabId());
			PField pField = pFieldMapper.selectByPrimaryKey(lab.getFieldId());
			PSchoolArea pSchoolArea = pSchoolAreaMapper.selectByPrimaryKey(pField.getAreaId());
			PSchool pSchool = pSchoolMapper.selectByPrimaryKey(pSchoolArea.getSchoolId());


			ExcelDevicePojo pojo = new ExcelDevicePojo();
			pojo.setDepartment(selfDefineMapper.selectDepartmentNameById(device.getDepartmentId()));
			pojo.setLabName(selfDefineMapper.selectLabNameByDeviceId(device.getId()));
			pojo.setCategory(String.join("、", findCategoryFull(device.getDeviceCategoryId())));
			pojo.setName(device.getName());
			pojo.setBatch(String.valueOf(device.getBatch()));
			pojo.setDescription(device.getDescription());
			pojo.setNumber(device.getNumber());
			pojo.setProductDate(device.getProductDate().toString());
			pojo.setModel(device.getModel());
			pojo.setSerial(device.getSerialNumber());
			pojo.setSchoolName(pSchool.getName());
			pojo.setSchoolArea(pSchoolArea.getName());

			ret.add(pojo);
		}
		return ret;
	}

	/**
	 * 查询设备分类，字符使用</>进行分割
	 * @param category
	 * @return
	 */
	Long findCategoryId(String category) {
		List<String> list = Arrays.asList(category.split("/"));
		Long id = null;
		System.out.println(list);
		for (String name: list){
			id = selfDefineMapper.selectCategoryIdByName(id, name);
			if (id == null) { break; }
		}
		return id;
	}

	@Override
	public List<String> findCategoryFull(Long id){
		List<String> ret = new ArrayList<String>();
		DeviceCategory category = deviceCategoryMapper.selectByPrimaryKey(id);
		if (category == null || category.getPid() == null){
			ret.add(category.getName());
			return ret;
		} else {
			ret = findCategoryFull(category.getPid());
			ret.add(category.getName());
			return ret;
		}
	}
}
