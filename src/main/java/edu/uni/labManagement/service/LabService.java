package edu.uni.labManagement.service;

import com.github.pagehelper.PageInfo;
import edu.uni.labManagement.bean.Lab;

import java.util.List;

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
	PageInfo<Lab> selectPage(int pageNum);

	/**
	 * 通过实验室ID查询实验室详情
	 * @param id
	 * @return
	 */
	Lab selectById(long id);
}
