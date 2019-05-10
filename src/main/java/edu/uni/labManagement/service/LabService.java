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
	boolean insert(Lab lab);

	boolean update(Lab lab);

	boolean deleted(Long id);

	PageInfo<Lab> selectPage(int pageNum);

	Lab selectById(long id);
}
