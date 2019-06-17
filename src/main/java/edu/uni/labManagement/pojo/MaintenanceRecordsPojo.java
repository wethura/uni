package edu.uni.labManagement.pojo;

import edu.uni.labManagement.bean.MaintenanceRecords;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/12 1:25
 */
public class MaintenanceRecordsPojo extends MaintenanceRecords {
	private String user;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "MaintenanceRecordsPojo{" +
				"user='" + user + '\'' +
				'}';
	}
}
