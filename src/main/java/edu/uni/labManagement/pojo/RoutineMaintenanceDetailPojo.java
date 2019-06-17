package edu.uni.labManagement.pojo;

import edu.uni.labManagement.bean.RoutineMaintenance;
import edu.uni.labManagement.bean.RoutineMaintenanceDetail;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/12 14:41
 */
public class RoutineMaintenanceDetailPojo {
	private RoutineMaintenance maintenance;

	private List<RoutineMaintenanceDetail> details;

	public RoutineMaintenance getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(RoutineMaintenance maintenance) {
		this.maintenance = maintenance;
	}

	public List<RoutineMaintenanceDetail> getDetails() {
		return details;
	}

	public void setDetails(List<RoutineMaintenanceDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "RoutineMaintenanceDetailPojo{" +
				"maintenancel=" + maintenance +
				", details=" + details +
				'}';
	}
}
