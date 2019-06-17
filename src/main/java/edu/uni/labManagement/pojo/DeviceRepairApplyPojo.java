package edu.uni.labManagement.pojo;

import edu.uni.labManagement.bean.DeviceRepairApply;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/12 16:03
 */
public class DeviceRepairApplyPojo extends DeviceRepairApply {
	private String deviceName;
	private String applyUser;
	private String reviewUser;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public String getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(String reviewUser) {
		this.reviewUser = reviewUser;
	}

	@Override
	public String toString() {
		return "DeviceRepairApplyPojo{" +
				"deviceName='" + deviceName + '\'' +
				", applyUser='" + applyUser + '\'' +
				", reviewUser='" + reviewUser + '\'' +
				"} " + super.toString();
	}
}
