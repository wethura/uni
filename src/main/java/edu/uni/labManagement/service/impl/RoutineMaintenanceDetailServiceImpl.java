package edu.uni.labManagement.service.impl;

import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.labManagement.bean.Device;
import edu.uni.labManagement.bean.RoutineMaintenance;
import edu.uni.labManagement.bean.RoutineMaintenanceDetail;
import edu.uni.labManagement.bean.RoutineMaintenanceDetailExample;
import edu.uni.labManagement.mapper.DeviceMapper;
import edu.uni.labManagement.mapper.RoutineMaintenanceDetailMapper;
import edu.uni.labManagement.mapper.SelfDefineMapper;
import edu.uni.labManagement.service.RoutineMaintenanceDetailService;
import edu.uni.labManagement.service.SelfDefineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
	@Autowired
	private AuthService authService;
	@Resource
	private DeviceMapper deviceMapper;
	@Resource
	private SelfDefineMapper selfDefineMapper;

	@Override
	public boolean insert(RoutineMaintenanceDetail routineMaintenanceDetail) {
		routineMaintenanceDetail.setDatetime(new Date());
		routineMaintenanceDetail.setDeleted(false);
		routineMaintenanceDetail.setByWho(((User)authService.getUser()).getId());
		routineMaintenanceDetail.setUniversityId(((User)authService.getUser()).getUniversityId());
		return routineMaintenanceDetailMapper.insert(routineMaintenanceDetail) > 0 ? true : false;
	}

	@Override
	public boolean update(RoutineMaintenanceDetail routineMaintenanceDetail) {
		RoutineMaintenanceDetail routineMaintenanceDetail1 = routineMaintenanceDetailMapper.selectByPrimaryKey(routineMaintenanceDetail.getId());
		routineMaintenanceDetail1.setDeleted(true);
		routineMaintenanceDetail1.setId(null);
		routineMaintenanceDetailMapper.insert(routineMaintenanceDetail1);

		routineMaintenanceDetail.setDatetime(new Date());
		routineMaintenanceDetail.setUniversityId(((User)authService.getUser()).getUniversityId());
		routineMaintenanceDetail.setByWho(((User)authService.getUser()).getId());
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

	@Override
	public List<Map<String, Object>> TurnFromMaintenanceDetail(List<RoutineMaintenanceDetail> list) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

		for (RoutineMaintenanceDetail detail: list){
			Map<String, Object> mp = new HashMap<String, Object>();

			mp.put("id", detail.getId());
			mp.put("description", detail.getDescription());
			mp.put("byWho", detail.getByWho());
			mp.put("datetime", detail.getDatetime());
			mp.put("deviceId", detail.getDeviceId());
			mp.put("isFault", detail.getIsFault());
			mp.put("routineMaintenanceId", detail.getRoutineMaintenanceId());
			mp.put("universityId", detail.getUniversityId());

			Device device = deviceMapper.selectByPrimaryKey(detail.getId());

			mp.put("deviceId", device.getId());
			mp.put("deviceName", device.getName());
			mp.put("deviceBatch", device.getBatch());
			mp.put("deviceDescription", device.getDescription());
			mp.put("deviceCategory", device.getDeviceCategoryId());
			mp.put("deviceModel", device.getModel());
			mp.put("deviceNumber", device.getNumber());
			mp.put("deviceProductDate", device.getProductDate());
			mp.put("deviceSerialNumber", device.getSerialNumber());
			mp.put("deviceIsMaster", device.getIsMaster());
			mp.put("deviceStatus", device.getStatus());
			mp.put("deviceDepartMentName", selfDefineMapper.selectDepartmentNameById(device.getDepartmentId()));

			ret.add(mp);
		}

		return ret;
	}
}
