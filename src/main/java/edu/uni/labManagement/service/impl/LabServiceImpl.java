package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.example.config.ExampleConfig;
import edu.uni.labManagement.bean.*;
import edu.uni.labManagement.mapper.*;
import edu.uni.labManagement.pojo.LabPojo;
import edu.uni.labManagement.service.LabService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/09 23:28
 */
@Service
public class LabServiceImpl implements LabService {

	@Resource
	private LabMapper labMapper;
	@Resource
	private LabAdminMapper labAdminMapper;
	@Resource
	private PFieldMapper fieldMapper;
	@Resource
	private DepartmentMapper departmentMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private PSchoolAreaMapper schoolAreaMapper;
	@Resource
	private PSchoolMapper schoolMapper;
	@Autowired
	private ExampleConfig globalConfig;


	@Override
	public boolean insert(Lab lab) {
		lab.setDeleted(false);
		lab.setDatetime(new Date());
		return labMapper.insert(lab) > 0 ? true : false;
	}

	@Override
	public boolean update(Lab lab) {
		Lab lab1 = labMapper.selectByPrimaryKey(lab.getId());
		lab1.setId(null);
		insert(lab1);
		System.out.println(lab1);
		lab.setDatetime(new Date());
		return labMapper.updateByPrimaryKeySelective(lab) > 0 ? true : false;
	}

	@Override
	public boolean deleted(Long id) {
		Lab lab = new Lab();
		lab.setId(id);
		lab.setDeleted(true);
		return labMapper.updateByPrimaryKeySelective(lab) > 0 ? true : false;
	}

	@Override
	public PageInfo<LabPojo> selectPage(int pageNum) {
		PageHelper.startPage(pageNum, globalConfig.getPageSize());
		LabExample example = new LabExample();
		LabExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		List<Lab> labs = labMapper.selectByExample(example);
		List<LabPojo> labPojos = new ArrayList<>();
		/**
		 * 此位置暂时没有数据接口
		 */
		for (int i= 0; i < labs.size(); i ++) {
			LabPojo labPojo = new LabPojo();
			labPojo.setPhone(labs.get(i).getPhone());
			BeanUtils.copyProperties(labs.get(i), labPojo);
			labPojos.add(labPojo);
			String addr = selectAddressByFieldID(labPojo.getFieldId());
			labPojos.get(i).setAddress(addr);
		}
//		System.out.println("\n============" + labs + "\n" + labPojos + "\n================\n");

		if (labs != null) {
			return new PageInfo<>(labPojos);
		} else {
			return null;
		}
	}

	@Override
	public LabPojo selectById(long id) {
		Lab lab = labMapper.selectByPrimaryKey(id);
		LabPojo pojo = new LabPojo();
		BeanUtils.copyProperties(lab, pojo);
		if (pojo.getFieldId() != null) {
			pojo.setAddress(selectAddressByFieldID(pojo.getFieldId()));
		}
		pojo.setAdminName(labAdminMapper.selectByLabId(pojo.getId()));
		return pojo;
	}

	protected String selectAddressByFieldID(long id) {
		return labMapper.selectAddressByFieldID(id);
	}

	/**
	 * 查询所有实验室的id和name
	 * @return List<Map<String,Object>>
	 * @author 招黄轩
	 */
	@Override
	public List<Map<String,Object>> selectByTwo(){
		return labMapper.selectByTwo();
	}

	/**
	 * 指定实验室管理员
	 * @param json 请求参数
	 * @return boolean
	 * @author 招黄轩
	 */
	@Override
	public boolean assignAdmin(Map<String,Object> json) {
		Long labId = Long.parseLong((String) json.get("labId"));
		Long adminId = Long.parseLong((String) json.get("adminId"));

		LabAdminExample example = new LabAdminExample();
		example.or().andLabIdEqualTo(labId).andDeletedEqualTo(false);
		List<LabAdmin> list = labAdminMapper.selectByExample(example);
		if(list.isEmpty()) {
			LabAdmin labAdmin = new LabAdmin();
			labAdmin.setLabId(labId);
			labAdmin.setAdminId(adminId);
			labAdmin.setUniversityId(1L);
			labAdmin.setDatetime(new Date());
			labAdmin.setByWho(1L);
			labAdmin.setDeleted(false);
			return labAdminMapper.insert(labAdmin) > 0;
		} else {
			LabAdmin labAdmin = list.get(0);
			labAdmin.setAdminId(adminId);
			return labAdminMapper.updateByPrimaryKeySelective(labAdmin) > 0;
		}
	}

	/**
	 * 查询单个实验室
	 * @param id 主键
	 * @return Map<String,Object>
	 * @author 招黄轩
	 */
	@Override
	public Map<String,Object> select(Long id) {
		LabExample example = new LabExample();
		example.or().andIdEqualTo(id).andDeletedEqualTo(false);
		List<Lab> list = labMapper.selectByExample(example);
		if(list.isEmpty()) return null;
		Lab lab = list.get(0);

		Map<String,Object> res = new HashMap<>();
		res.put("id", lab.getId());
		res.put("name", lab.getName());

		PField field = fieldMapper.selectByPrimaryKey(lab.getFieldId());
		PSchoolArea area = schoolAreaMapper.selectByPrimaryKey(field.getAreaId());
		PSchool school = schoolMapper.selectByPrimaryKey(area.getSchoolId());
		res.put("fieldName", school.getName() + "/" + area.getName() + "/" + field.getName());

		Department department = departmentMapper.selectByPrimaryKey(lab.getDepartmentId());
		res.put("departmentName", department.getName());

		LabAdminExample example1 = new LabAdminExample();
		example1.or().andLabIdEqualTo(lab.getId()).andDeletedEqualTo(false);
		List<LabAdmin> list1 = labAdminMapper.selectByExample(example1);
		if(list1.isEmpty()) {
			res.put("adminName", null);
		} else {
			LabAdmin labAdmin = list1.get(0);
			User user = userMapper.selectByPrimaryKey(labAdmin.getAdminId());
			res.put("adminName", user.getUserName());
		}
		return res;
	}

	/**
	 * 根据Example分页查询
	 * @param example Example类
	 * @param pageNum 页码
	 * @param pageSize 每页显示条数
	 * @return PageInfo<Map<String,Object>>
	 * @author 招黄轩
	 */
	public PageInfo<Map<String,Object>> selectPageByExample(LabExample example, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize); //开启分页查询，仅第一次查询时生效
		List<Lab> res = labMapper.selectByExample(example);

		List<Map<String,Object>> list = new LinkedList<>();
		for(Lab lab : res) {
			list.add(select(lab.getId()));
		}

		PageInfo<Lab> temp = new PageInfo<>(res);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
		temp.setList(null);
		BeanUtils.copyProperties(temp, pageInfo);
		pageInfo.setList(list);
		return pageInfo;
	}

	/**
	 * 分页查询所有实验室
	 * @param pageNum 页码
	 * @param pageSize 每页显示条数
	 * @return PageInfo<Map<String,Object>>
	 * @author 招黄轩
	 */
	@Override
	public PageInfo<Map<String,Object>> selectPage(int pageNum, int pageSize) {
		LabExample example = new LabExample();
		example.or().andDeletedEqualTo(false);
		return selectPageByExample(example, pageNum, pageSize);
	}

	/**
	 * 分页高级筛选所有实验室
	 * @param json 请求参数
	 * @return PageInfo<Map<String,Object>>
	 * @author 招黄轩
	 */
	@Override
	public PageInfo<Map<String,Object>> filterAll(Map<String,Object> json) {
		PFieldExample example = new PFieldExample();
		List<Integer> fieldNameList = (List<Integer>) json.get("fieldName");
		List<Long> fieldNameListLong = new LinkedList<>();
		if(fieldNameList == null) {
			PFieldExample fieldExample = new PFieldExample();
			fieldExample.or().andDeletedEqualTo(0);
			List<PField> fieldList = fieldMapper.selectByExample(fieldExample);
			for(PField field : fieldList) fieldNameListLong.add(field.getId());
		} else {
			fieldNameListLong.add((long)fieldNameList.get(fieldNameList.size()-1));
		}
		if(fieldNameListLong.isEmpty()) fieldNameListLong.add(-12345L);
		example.or().andIdIn(fieldNameListLong);
		List<PField> list = fieldMapper.selectByExample(example);

		List<Long> fieldIds = new LinkedList<>();
		for(PField field : list) {
			fieldIds.add(field.getId());
		}
		if(fieldIds.isEmpty()) fieldIds.add(-12345L);


		DepartmentExample example1 = new DepartmentExample();
		List<Integer> departmentNameList = (List<Integer>) json.get("departmentName");
		List<Long> departmentNameListLong = new LinkedList<>();
		if(departmentNameList == null) {
			DepartmentExample departmentExample = new DepartmentExample();
			departmentExample.or().andDeletedEqualTo(false);
			List<Department> departmentList = departmentMapper.selectByExample(departmentExample);
			for(Department department : departmentList) departmentNameListLong.add(department.getId());
		} else {
			departmentNameListLong.add((long)departmentNameList.get(departmentNameList.size()-1));
		}
		if(departmentNameListLong.isEmpty()) departmentNameListLong.add(-12345L);
		example1.or().andIdIn(departmentNameListLong);
		List<Department> list1 = departmentMapper.selectByExample(example1);

		List<Long> departmentIds = new LinkedList<>();
		for(Department department : list1) {
			departmentIds.add(department.getId());
		}
		if(departmentIds.isEmpty()) departmentIds.add(-12345L);


		UserExample example2 = new UserExample();
		json.putIfAbsent("adminName", "");
		example2.or().andUserNameLike("%" + (String) json.get("adminName") + "%");
		List<User> list2 = userMapper.selectByExample(example2);

		List<Long> adminIds = new LinkedList<>();
		for(User user : list2) {
			adminIds.add(user.getId());
		}
		if(adminIds.isEmpty()) adminIds.add(-12345L);


		LabAdminExample example3 = new LabAdminExample();
		example3.or().andAdminIdIn(adminIds);
		List<LabAdmin> list3 = labAdminMapper.selectByExample(example3);

		List<Long> labIds = new LinkedList<>();
		for(LabAdmin labAdmin : list3) {
			labIds.add(labAdmin.getLabId());
		}
		if(labIds.isEmpty()) labIds.add(-12345L);


		json.putIfAbsent("pageNum", "1");
		json.putIfAbsent("pageSize", "1000");
		LabExample example4 = new LabExample();
		json.putIfAbsent("name", "");
		example4.or().andNameLike("%" + (String) json.get("name") + "%")
				.andFieldIdIn(fieldIds).andDepartmentIdIn(departmentIds)
				.andIdIn(labIds).andDeletedEqualTo(false);
		return selectPageByExample(example4, Integer.parseInt((String) json.get("pageNum")),
				Integer.parseInt((String) json.get("pageSize")));
	}
}
