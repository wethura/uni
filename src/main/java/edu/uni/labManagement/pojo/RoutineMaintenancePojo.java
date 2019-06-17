package edu.uni.labManagement.pojo;

import edu.uni.labManagement.bean.RoutineMaintenance;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/12 10:03
 */
public class RoutineMaintenancePojo extends RoutineMaintenance {
	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "RoutineMaintenancePojo{" +
				"user='" + user + '\'' +
				"} " + super.toString();
	}
}
