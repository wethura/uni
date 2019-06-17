package edu.uni.labManagement.service.impl;

import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.RoutineMaintenance;
import edu.uni.labManagement.bean.RoutineMaintenanceDetail;
import edu.uni.labManagement.bean.RoutineMaintenanceExample;
import edu.uni.labManagement.mapper.MaintenanceRecordsMapper;
import edu.uni.labManagement.mapper.RoutineMaintenanceDetailMapper;
import edu.uni.labManagement.mapper.RoutineMaintenanceMapper;
import edu.uni.labManagement.pojo.RoutineMaintenancePojo;
import edu.uni.labManagement.service.RoutineMaintenanceService;
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
 * @date 2019/05/06 0:11
 */
@Service
public class RoutineMaintenanceServiceImpl implements RoutineMaintenanceService {

	@Resource
	private RoutineMaintenanceMapper routineMaintenanceMapper;
	@Resource
	private MaintenanceRecordsMapper maintenanceRecordsMapper;
	@Resource
	private RoutineMaintenanceDetailMapper routineMaintenanceDetailMapper;
	@Autowired
	private AuthService authService;

	@Override
	public boolean insert(RoutineMaintenance routineMaintenance) {
		routineMaintenance.setDeleted(false);
		routineMaintenance.setDatetime(new Date());
		routineMaintenance.setByWho(((User)authService.getUser()).getId());
		routineMaintenance.setUniversityId(((User)authService.getUser()).getUniversityId());
		return routineMaintenanceMapper.insert(routineMaintenance) > 0 ? true : false;
	}

	@Override
	public boolean update(RoutineMaintenance routineMaintenance) {
		return routineMaintenanceMapper.updateByPrimaryKey(routineMaintenance) > 0 ? true : false;
	}

	@Override
	public boolean deleted(long id) {
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
	public List<RoutineMaintenancePojo> listByLabId(long labId) {
		RoutineMaintenanceExample example = new RoutineMaintenanceExample();
		RoutineMaintenanceExample.Criteria criteria = example.createCriteria();
		criteria.andLabIdEqualTo(labId);
		criteria.andDeletedEqualTo(false);
		List<RoutineMaintenance>list = routineMaintenanceMapper.selectByExample(example);
		List<RoutineMaintenancePojo> rmpojo = new ArrayList<>();
		for (RoutineMaintenance routineMaintenance : list) {
			RoutineMaintenancePojo pojo = new RoutineMaintenancePojo();
			BeanUtils.copyProperties(routineMaintenance, pojo);
			if(pojo.getByWho() != null) {
				pojo.setUser(maintenanceRecordsMapper.selectUserById(pojo.getByWho()));
			}
			rmpojo.add(pojo);
		}
		return rmpojo;
	}

	@Override
	public boolean createMaintenance(RoutineMaintenance maintenance, List<RoutineMaintenanceDetail> details) {

		boolean success = true;

		maintenance.setDeleted(false);
		maintenance.setDatetime(new Date());
		success = success && (routineMaintenanceMapper.insert(maintenance) > 0 ? true : false);

		for (RoutineMaintenanceDetail detail : details) {
			detail.setDeleted(false);
			detail.setDatetime(new Date());
			detail.setRoutineMaintenanceId(maintenance.getId());
			if (detail.getDescription() == "" || detail.getDescription() == null) {
				detail.setDescription("本次维护正常进行...<本记录为系统默认值>");
			}
			success = success && (routineMaintenanceDetailMapper.insert(detail) > 0 ? true : false);
		}
		return success;
	}
}
