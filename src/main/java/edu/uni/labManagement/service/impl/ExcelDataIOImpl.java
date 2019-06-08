package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.*;
import edu.uni.labManagement.mapper.*;
import edu.uni.labManagement.pojo.ExcelDevicePojo;
import edu.uni.labManagement.pojo.ExcelLabPojo;
import edu.uni.labManagement.service.ExcelDataIO;
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


	@Override
	public String ExcelLabPojoImport(List<Object> os) throws Exception {
		ExcelLabPojo pojo = null;

		List<ExcelLabPojo> list = new ArrayList<ExcelLabPojo>();

//      标题头检查
		if (os != null && os.size() > 0) {
			pojo = (ExcelLabPojo) os.get(0);
			System.out.println(pojo);
			if (!pojo.getName().startsWith("实验室名称") ||
					!pojo.getAddress().startsWith("地点") ||
					!pojo.getAdmins().startsWith("主管账号") ||
					!pojo.getDepartment().startsWith("行政部门") ||
					!pojo.getDescription().startsWith("实验室描述") ||
					!pojo.getPhone().startsWith("实验室电话") ||
					!pojo.getEname().startsWith("实验室英文名称")) {
				throw new Exception("您的数据标题对应不上，请标题栏顺序按照：实验室名称、实验室英文名称、行政部门、实验室电话、地点、实验室描述、主管账号");
			}
		}

		System.out.println(os.size());
//		一边数据检查一边插入数据，失败就抛出异常进行事务回滚
		for(int i = 1; i < os.size(); i ++) {
			pojo = (ExcelLabPojo) os.get(i);
			Lab lab = new Lab();

			Long departmentId = selfDefineMapper.selectDepartmentIdByName(pojo.getDepartment());
			Long filedId = selfDefineMapper.selectFieldIdByName(pojo.getAddress());
			if (departmentId == null|| departmentId <= 0 ){
				throw new Exception("第" + i + "行的 《部门》 不存在，请注意名称的正确性！");
			} else if(filedId <= 0 || filedId == null) {
				throw new Exception("第" + i + "行的 《地点》 不存在，请注意名称的正确性！");
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

//		标题头检查
		if (os != null && os.size() > 0) {
			pojo = (ExcelDevicePojo) os.get(0);
			System.out.println(pojo);
			if (!pojo.getId().startsWith("设备主键编号") ||
					!pojo.getCategory().startsWith("设备类别") ||
					!pojo.getDepartment().startsWith("所属部门") ||
					!pojo.getDescription().startsWith("设备描述") ||
					!pojo.getModel().startsWith("设备型号") ||
					!pojo.getLabName().startsWith("实验室名称") ||
					!pojo.getName().startsWith("设备名称") ||
					!pojo.getProductDate().startsWith("设备生产日期") ||
					!pojo.getNumber().startsWith("设备编号") ||
					!pojo.getSerial().startsWith("序列号")){
				return "您的数据标题对应不上，请标题栏顺序按照：设备主键编号、设备类别、设备名称、所属部门、" +
					   "实验室名称、设备型号、设备编号、序列号、设备描述、设备生产日期";
			}
		}

		System.out.println(os.size());
//		一边数据检查一边插入数据，失败就抛出异常进行事务回滚
		for (int i = 1; i < os.size(); i ++) {
			pojo = (ExcelDevicePojo) os.get(i);
			System.out.println(pojo);

			if (pojo.getSerial() == null && pojo.getNumber()==null && pojo.getProductDate()==null && pojo.getLabName() == null && pojo.getName() == null
			&& pojo.getDepartment() == null && pojo.getBatch() == null){continue;}

//			查找设备类别
			Long categoryId = findCategoryId(pojo.getCategory());
//			查找设备部门
			Long departmentId = selfDefineMapper.selectDepartmentIdByName(pojo.getDepartment());
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

			boolean success = deviceMapper.insert(device) > 0 ? true : false;
			if (!success) { throw new Exception("数据导入失败！");}
		}

		return "您的数据已经成功导入！";
	}


	@Override
	public List<ExcelLabPojo> ExcelLabPojoExport() throws Exception {

		LabExample example = new LabExample();
		LabExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		List<Lab> list = labMapper.selectByExample(example);

		List<ExcelLabPojo> ret = new ArrayList<ExcelLabPojo>();

		for (Lab lab: list){

			ExcelLabPojo pojo = new ExcelLabPojo();
			pojo.setAddress(selfDefineMapper.selectFieldNameById(lab.getFieldId()));
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
	public List<ExcelDevicePojo> ExcelDevicePojoExport(Long labId) throws Exception {
		List<Device> list = deviceMapper.selectByLabId(labId);

		List<ExcelDevicePojo> ret = new ArrayList<ExcelDevicePojo>();

		for (Device device: list){

			ExcelDevicePojo pojo = new ExcelDevicePojo();
			pojo.setName(device.getName());
			pojo.setBatch(String.valueOf(device.getBatch()));
			pojo.setDepartment(selfDefineMapper.selectDepartmentNameById(device.getDepartmentId()));
			pojo.setDescription(device.getDescription());
			pojo.setLabName(selfDefineMapper.selectLabNameByDeviceId(device.getId()));
			pojo.setCategory(String.join("、", findCategoryFull(device.getDeviceCategoryId())));
			pojo.setId(String.valueOf(device.getId()));
			pojo.setNumber(device.getNumber());
			pojo.setProductDate(device.getProductDate().toString());
			pojo.setModel(device.getModel());
			pojo.setSerial(device.getSerialNumber());

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

	List<String> findCategoryFull(Long id){
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
