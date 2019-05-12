package edu.uni.labManagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.uni.example.config.ExampleConfig;
import edu.uni.labManagement.bean.Lab;
import edu.uni.labManagement.bean.LabExample;
import edu.uni.labManagement.mapper.LabMapper;
import edu.uni.labManagement.pojo.LabPojo;
import edu.uni.labManagement.service.LabService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
		return pojo;
	}

	protected String selectAddressByFieldID(long id) {
		return labMapper.selectAddressByFieldID(id);
	}
}
