package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.example.config.ExampleConfig;
import edu.uni.labManagement.bean.Lab;
import edu.uni.labManagement.bean.LabExample;
import edu.uni.labManagement.mapper.LabMapper;
import edu.uni.labManagement.service.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
	public PageInfo<Lab> selectPage(int pageNum) {
		PageHelper.startPage(pageNum, globalConfig.getPageSize());
		LabExample example = new LabExample();
		LabExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		List<Lab> labs = labMapper.selectByExample(example);
		if (labs != null) {
			return new PageInfo<>(labs);
		} else {
			return null;
		}
	}

	@Override
	public Lab selectById(long id) {
		return labMapper.selectByPrimaryKey(id);
	}
}
