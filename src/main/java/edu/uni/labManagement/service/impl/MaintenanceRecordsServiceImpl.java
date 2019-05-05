package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.MaintenanceRecords;
import edu.uni.labManagement.bean.MaintenanceRecordsExample;
import edu.uni.labManagement.mapper.MaintenanceRecordsMapper;
import edu.uni.labManagement.service.MaintenanceRecordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

	@Override
	public boolean insert(MaintenanceRecords maintenanceRecords) {
		maintenanceRecords.setDatetime(LocalDateTime.now());
		maintenanceRecords.setDeleted(false);
		return maintenanceRecordsMapper.insert(maintenanceRecords) > 0 ? true : false;
	}

	@Override
	public boolean update(MaintenanceRecords maintenanceRecords) {
		return maintenanceRecordsMapper.updateByPrimaryKeySelective(maintenanceRecords) > 0 ? true : false;
	}

	@Override
	public boolean delete(long id) {
		return maintenanceRecordsMapper.deleteByPrimaryKey(id) > 0 ? true : false;
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
	public List<MaintenanceRecords> listByDeviceId(long id) {
		MaintenanceRecordsExample example = new MaintenanceRecordsExample();
		MaintenanceRecordsExample.Criteria criteria = example.createCriteria();
		criteria.andDeviceIdEqualTo(id);
		criteria.andDeletedEqualTo(false);
		return maintenanceRecordsMapper.selectByExample(example);
	}
}
