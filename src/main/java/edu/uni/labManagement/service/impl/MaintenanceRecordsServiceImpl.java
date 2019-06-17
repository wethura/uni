package edu.uni.labManagement.service.impl;

import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.MaintenanceRecords;
import edu.uni.labManagement.bean.MaintenanceRecordsExample;
import edu.uni.labManagement.mapper.MaintenanceRecordsMapper;
import edu.uni.labManagement.pojo.MaintenanceRecordsPojo;
import edu.uni.labManagement.service.MaintenanceRecordsService;
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
 * @date 2019/05/04 15:12
 */
@Service
public class MaintenanceRecordsServiceImpl implements MaintenanceRecordsService {

	@Resource
	private MaintenanceRecordsMapper maintenanceRecordsMapper;
	@Autowired
	private AuthService authService;

	@Override
	public boolean insert(MaintenanceRecords maintenanceRecords) {
		maintenanceRecords.setDatetime(new Date());
		maintenanceRecords.setDeleted(false);
		maintenanceRecords.setByWho(((User)authService.getUser()).getId());
		maintenanceRecords.setUniversityId(((User)authService.getUser()).getUniversityId());
		return maintenanceRecordsMapper.insert(maintenanceRecords) > 0 ? true : false;
	}

	@Override
	public boolean update(MaintenanceRecords maintenanceRecords) {
		MaintenanceRecords maintenanceRecordsNew = maintenanceRecordsMapper.selectByPrimaryKey(maintenanceRecords.getId());
		maintenanceRecordsNew.setDeleted(true);
		maintenanceRecords.setId(null);
		maintenanceRecordsMapper.insert(maintenanceRecordsNew);

		maintenanceRecords.setByWho(((User)authService.getUser()).getId());
		maintenanceRecords.setUniversityId(((User)authService.getUser()).getUniversityId());
		return maintenanceRecordsMapper.updateByPrimaryKeySelective(maintenanceRecords) > 0 ? true : false;
	}

	@Override
	public boolean delete(long id) {
		MaintenanceRecords records = new MaintenanceRecords();
		records.setDeleted(true);
		records.setId(id);
		return maintenanceRecordsMapper.updateByPrimaryKeySelective(records) > 0 ? true : false;
	}

	@Override
	public MaintenanceRecords listByRepairApplyId(long id) {
		MaintenanceRecordsExample example = new MaintenanceRecordsExample();
		MaintenanceRecordsExample.Criteria criteria = example.createCriteria();
		criteria.andDeviceRepairApplyIdEqualTo(id);
		criteria.andDeletedEqualTo(false);
		List<MaintenanceRecords> list = maintenanceRecordsMapper.selectByExample(example);
		return list != null ? list.get(0) : null;
	}

	@Override
	public List<MaintenanceRecordsPojo> listByDeviceId(long id) {
		MaintenanceRecordsExample example = new MaintenanceRecordsExample();
		MaintenanceRecordsExample.Criteria criteria = example.createCriteria();
		criteria.andDeviceIdEqualTo(id);
		criteria.andDeletedEqualTo(false);
		List<MaintenanceRecords>records =  maintenanceRecordsMapper.selectByExample(example);

		List<MaintenanceRecordsPojo> pojos = new ArrayList<>();
		for (MaintenanceRecords record : records) {
			MaintenanceRecordsPojo pojo = new MaintenanceRecordsPojo();
			BeanUtils.copyProperties(record, pojo);
			if (pojo.getByWho() != null) {
				pojo.setUser(selectUserById(pojo.getByWho()));
			}
			pojos.add(pojo);
		}
		return pojos;
	}

	@Override
	public List<MaintenanceRecordsPojo> listByLabId(long labId) {
		List<MaintenanceRecords>records = maintenanceRecordsMapper.selectByLabId(labId);
		List<MaintenanceRecordsPojo> recordsPojos = new ArrayList<>();
		for (int i = 0; i < records.size(); i ++) {
			MaintenanceRecordsPojo maintenanceRecordsPojo = new MaintenanceRecordsPojo();
			BeanUtils.copyProperties(records.get(i), maintenanceRecordsPojo);
			System.out.println(records.get(i) + "\n" + maintenanceRecordsPojo.getByWho());
			String user = selectUserById(maintenanceRecordsPojo.getByWho());
			maintenanceRecordsPojo.setUser(user);
			recordsPojos.add(maintenanceRecordsPojo);
		}
		return recordsPojos;
	}

	public String selectUserById(long id) {
		return maintenanceRecordsMapper.selectUserById(id);
	}
}
