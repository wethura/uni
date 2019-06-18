package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.User;
import edu.uni.labManagement.bean.*;
import edu.uni.labManagement.config.LabManagementConfig;
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
	private LabManagementConfig globalConfig;
	@Resource
	private LabDeviceMapper labDeviceMapper;
	@Autowired
	private AuthService authService;

	@Override
	public boolean insert(Lab lab) {
		lab.setDeleted(false);
		lab.setDatetime(new Date());
		lab.setByWho(((edu.uni.auth.bean.User)authService.getUser()).getId());
		lab.setUniversityId(((edu.uni.auth.bean.User)authService.getUser()).getUniversityId());
		return labMapper.insert(lab) > 0 ? true : false;
	}

	@Override
	public boolean update(Lab lab) {
		Lab lab1 = labMapper.selectByPrimaryKey(lab.getId());
		lab1.setId(null);
		insert(lab1);
		System.out.println(lab1);
		lab.setDatetime(new Date());
		lab.setUniversityId(((edu.uni.auth.bean.User)authService.getUser()).getUniversityId());
		lab.setByWho(((edu.uni.auth.bean.User)authService.getUser()).getId());
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
	public Map<String, Object> selectPage(int pageNum) {

		LabExample example = new LabExample();
		LabExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false)
				.andUniversityIdEqualTo(authService.getUser().getUniversityId());

		List<Lab> labs = labMapper.selectByExample(example);
		Map<String, Object> res = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		/**
		 * 此位置暂时没有数据接口
		 */
		for (int i = (pageNum - 1) * globalConfig.getPageSize(); i < pageNum * globalConfig.getPageSize() && i < labs.size(); i ++) {

			Lab lab = labs.get(i);

			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("name", lab.getName());
			mp.put("eName", lab.getEname());
			mp.put("id", lab.getId());
			mp.put("departmentId", lab.getDepartmentId());
			mp.put("fieldId", lab.getFieldId());
			mp.put("phone", lab.getPhone());
			String addr = selectAddressByFieldID(lab.getFieldId());
			mp.put("address", addr);
			mp.put("datetime", lab.getDatetime());
			mp.put("department", departmentMapper.selectByPrimaryKey(lab.getDepartmentId()).getName());
			list.add(mp);
		}
		res.put("list", list);
		res.put("total", labs.size());
		res.put("pageSize", globalConfig.getPageSize());

		return res;
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
	 * 查找本学校所有的Ename，Name
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectAllEname() {

		LabExample example = new LabExample();
		LabExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false)
				.andUniversityIdEqualTo(authService.getUser().getUniversityId());
		List<Lab> labs = labMapper.selectByExample(example);
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < labs.size(); i ++){
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("Id", labs.get(i).getId());
			mp.put("EName", labs.get(i).getEname());
			mp.put("Name", labs.get(i).getName());

			ret.add(mp);
		}
		return ret;
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
		String name = (String) json.get("name");
		List<Long> fieldIds = Tool.listIntToLong((List<Integer>) json.get("fieldName"));
		List<Long> departmentIds = Tool.listIntToLong((List<Integer>) json.get("departmentName"));
		String adminName = (String) json.get("adminName");
		Integer pageNum = (Integer) json.get("pageNum");
		Integer pageSize = (Integer) json.get("pageSize");
		if(pageNum == null) pageNum = 1;
		if(pageSize == null) pageSize = 1000;

		LabExample example = new LabExample();
		LabExample.Criteria criteria = example.createCriteria();
		if(name != null) {
			criteria.andNameLike("%" + name + "%");
		}
		if(fieldIds != null) {
			while (fieldIds.size() > 1) fieldIds.remove(0);
			if(fieldIds.isEmpty()) fieldIds.add(-9L);
			criteria.andFieldIdIn(fieldIds);
		}
		if(departmentIds != null) {
			while (departmentIds.size() > 1) departmentIds.remove(0);
			if(departmentIds.isEmpty()) departmentIds.add(-9L);
			criteria.andDepartmentIdIn(departmentIds);
		}
		if(adminName != null) {
			List<Long> adminIds = Tool.getIdsByCriteria(new UserExample().or().
					andUserNameLike("%" + adminName + "%"), "getId");
			List<Long> ids = Tool.getIdsByCriteria(new LabAdminExample().or().
					andAdminIdIn(adminIds), "getLabId");
			criteria.andIdIn(ids);
		}
		criteria.andDeletedEqualTo(false);
		return selectPageByExample(example, pageNum, pageSize);
	}

	/**
	 * 分页高级筛选所有实验室2
	 * @param json 请求参数
	 * @return PageInfo<Map<String,Object>>
	 * @author 招黄轩
	 */
	@Override
	public PageInfo<Map<String,Object>> filterAll2(Map<String,Object> json) {
		List<Long> departmentIds = Tool.listIntToLong((List<Integer>) json.get("departmentIds"));
		List<String> names = (List<String>) json.get("names");
		List<String> enames = (List<String>) json.get("enames");
		List<Long> fieldIds = Tool.listIntToLong((List<Integer>) json.get("fieldIds"));
		Integer pageNum = (Integer) json.get("pageNum");
		Integer pageSize = (Integer) json.get("pageSize");
		if(pageNum == null) pageNum = 1;
		if(pageSize == null) pageSize = 10;

		LabExample example = new LabExample();
		LabExample.Criteria criteria = example.createCriteria();
		if(departmentIds != null && !departmentIds.isEmpty()) {
			criteria.andDepartmentIdIn(departmentIds);
		}
		if(names != null && !names.isEmpty()) {
			LabExample example1 = new LabExample();
			for(String name : names) example1.or().andNameLike("%" + name + "%");
			List<Long> ids = Tool.getIdsByExample(example1, "getId");
			criteria.andIdIn(ids);
		}
		if(enames != null && !enames.isEmpty()) {
			LabExample example1 = new LabExample();
			for(String ename : enames) example1.or().andNameLike("%" + ename + "%");
			List<Long> ids = Tool.getIdsByExample(example1, "getId");
			criteria.andIdIn(ids);
		}
		if(fieldIds != null && !fieldIds.isEmpty()) {
			Long schoolId = fieldIds.get(0);
			Long areaId = fieldIds.size() > 1 ? fieldIds.get(1) : null;
			Long fieldId = fieldIds.size() > 2 ? fieldIds.get(2) : null;
			List<Long> ids;
			if(fieldId != null) {
				ids = Tool.getIdsByCriteria(new LabExample().or()
						.andFieldIdEqualTo(fieldId), "getId");
			} else {
				if(areaId != null) {
					List<Long> list = Tool.getIdsByCriteria(new PFieldExample().or()
							.andAreaIdEqualTo(areaId), "getId");
					ids = Tool.getIdsByCriteria(new LabExample().or()
							.andFieldIdIn(list), "getId");
				} else {
					List<Long> list = Tool.getIdsByCriteria(new PSchoolAreaExample().or()
							.andSchoolIdEqualTo(schoolId), "getId");
					List<Long> list1 = Tool.getIdsByCriteria(new PFieldExample().or()
							.andAreaIdIn(list), "getId");
					ids = Tool.getIdsByCriteria(new LabExample().or()
							.andIdIn(list1), "getId");
				}
			}
			criteria.andIdIn(ids);
		}
		criteria.andDeletedEqualTo(false);
		return selectPageByExample(example, pageNum, pageSize);
	}

}
