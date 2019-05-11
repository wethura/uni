package edu.uni.labManagement.service.impl;

import edu.uni.labManagement.bean.RoutineMaintenance;
import edu.uni.labManagement.bean.RoutineMaintenanceDetail;
import edu.uni.labManagement.bean.RoutineMaintenanceDetailExample;
import edu.uni.labManagement.mapper.RoutineMaintenanceDetailMapper;
import edu.uni.labManagement.service.RoutineMaintenanceDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/11 2:02
 */
@Service
public class RoutineMaintenanceDetailServiceImpl implements RoutineMaintenanceDetailService {

	@Resource
	private RoutineMaintenanceDetailMapper routineMaintenanceDetailMapper;

	@Override
	public boolean insert(RoutineMaintenanceDetail routineMaintenanceDetail) {
		routineMaintenanceDetail.setDatetime(new Date());
		routineMaintenanceDetail.setDeleted(false);
		return routineMaintenanceDetailMapper.insert(routineMaintenanceDetail) > 0 ? true : false;
	}

	@Override
	public boolean update(RoutineMaintenanceDetail routineMaintenanceDetail) {
		RoutineMaintenanceDetail routineMaintenanceDetail1 = routineMaintenanceDetailMapper.selectByPrimaryKey(routineMaintenanceDetail.getId());
		routineMaintenanceDetail1.setDeleted(true);
		routineMaintenanceDetail1.setId(null);
		routineMaintenanceDetailMapper.insert(routineMaintenanceDetail1);

		routineMaintenanceDetail.setDatetime(new Date());
		return routineMaintenanceDetailMapper.updateByPrimaryKeySelective(routineMaintenanceDetail) > 0 ? true : false;
	}

	@Override
	public boolean deleted(long id) {
		RoutineMaintenanceDetail routineMaintenanceDetail = new RoutineMaintenanceDetail();
		routineMaintenanceDetail.setId(id);
		routineMaintenanceDetail.setDeleted(true);
		return routineMaintenanceDetailMapper.insert(routineMaintenanceDetail) > 0 ? true : false;
	}

	@Override
	public List<RoutineMaintenanceDetail> listByRoutineMaintenanceId(long routineMaintenanceId) {
		RoutineMaintenanceDetailExample example = new RoutineMaintenanceDetailExample();
		RoutineMaintenanceDetailExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false)
				.andRoutineMaintenanceIdEqualTo(routineMaintenanceId);

		return routineMaintenanceDetailMapper.selectByExample(example);
	}

	@Override
	public List<RoutineMaintenanceDetail> listByDeviceId(long deviceId) {
		RoutineMaintenanceDetailExample example = new RoutineMaintenanceDetailExample();
		RoutineMaintenanceDetailExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false)
				.andDeviceIdEqualTo(deviceId);

		return routineMaintenanceDetailMapper.selectByExample(example);
	}
}
