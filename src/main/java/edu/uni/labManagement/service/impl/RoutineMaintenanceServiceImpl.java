package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.RoutineMaintenance;
import edu.uni.labManagement.bean.RoutineMaintenanceExample;
import edu.uni.labManagement.mapper.RoutineMaintenanceMapper;
import edu.uni.labManagement.service.RoutineMaintenanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/06 0:11
 */
@Service
public class RoutineMaintenanceServiceImpl implements RoutineMaintenanceService {

	@Resource
	private RoutineMaintenanceMapper routineMaintenanceMapper;

	@Override
	public boolean insert(RoutineMaintenance routineMaintenance) {
		routineMaintenance.setDeleted(false);
		routineMaintenance.setDatetime(new Date());
		return routineMaintenanceMapper.insert(routineMaintenance) > 0 ? true : false;
	}

	@Override
	public boolean update(RoutineMaintenance routineMaintenance) {
		return routineMaintenanceMapper.updateByPrimaryKey(routineMaintenance) > 0 ? true : false;
	}

	@Override
	public boolean deleted(long id) {
//	使用deleted注释删除数据
		RoutineMaintenance routineMaintenance = new RoutineMaintenance();
		routineMaintenance.setId(id);
		routineMaintenance.setDeleted(true);

		return routineMaintenanceMapper.updateByPrimaryKeySelective(routineMaintenance) > 0 ? true : false;
	}

	@Override
	public List<RoutineMaintenance> listByDeviceId(long deviceId) {
		return routineMaintenanceMapper.listByDeviceId(deviceId);
	}

	@Override
	public List<RoutineMaintenance> listByLabId(long labId) {
		RoutineMaintenanceExample example = new RoutineMaintenanceExample();
		RoutineMaintenanceExample.Criteria criteria = example.createCriteria();
		criteria.andLabIdEqualTo(labId);
		criteria.andDeletedEqualTo(false);
		return routineMaintenanceMapper.selectByExample(example);
	}
}
