package edu.uni.labManagement.service;

import com.github.pagehelper.PageInfo;
import edu.uni.labManagement.bean.Lab;
import edu.uni.labManagement.pojo.LabPojo;

import java.util.List;
import java.util.Map;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/09 23:14
 */
public interface LabService {
	/**
	 * 增
	 * @param lab
	 * @return
	 */
	boolean insert(Lab lab);

	/**
	 * 改
	 * @param lab
	 * @return
	 */
	boolean update(Lab lab);

	/**
	 * 删
	 * @param id
	 * @return
	 */
	boolean deleted(Long id);

	/**
	 * 分页查询所有设备，显示第pageNum页的实验室
	 * @param pageNum
	 * @return
	 */
	PageInfo<LabPojo> selectPage(int pageNum);

	/**
	 * 通过实验室ID查询实验室详情
	 * @param id
	 * @return
	 */
	LabPojo selectById(long id);

	/**
	 * 查询所有实验室的id和name
	 * @return List<Map<String,Object>>
	 * @author 招黄轩
	 */
	List<Map<String,Object>> selectByTwo();


	List<Map<String,Object>> selectAllEname();

	/**
	 * 指定实验室管理员
	 * @param json 请求参数
	 * @return boolean
	 * @author 招黄轩
	 */
	boolean assignAdmin(Map<String, Object> json);

	/**
	 * 查询单个实验室
	 * @param id 主键
	 * @return Map<String,Object>
	 * @author 招黄轩
	 */
	Map<String,Object> select(Long id);

	/**
	 * 分页查询所有实验室
	 * @param pageNum 页码
	 * @param pageSize 每页显示条数
	 * @return PageInfo<Map<String,Object>>
	 * @author 招黄轩
	 */
	PageInfo<Map<String,Object>> selectPage(int pageNum, int pageSize);

	/**
	 * 分页高级筛选所有实验室
	 * @param json 请求参数
	 * @return PageInfo<Map<String,Object>>
	 * @author 招黄轩
	 */
	PageInfo<Map<String,Object>> filterAll(Map<String, Object> json);

	/**
	 * 分页高级筛选所有实验室2
	 * @param json 请求参数
	 * @return PageInfo<Map<String,Object>>
	 * @author 招黄轩
	 */
	PageInfo<Map<String,Object>> filterAll2(Map<String, Object> json);
}
