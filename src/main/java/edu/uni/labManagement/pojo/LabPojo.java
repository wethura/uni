package edu.uni.labManagement.pojo;

import edu.uni.labManagement.bean.Lab;

import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/11 23:47
 */
public class LabPojo extends Lab {
	private String address;

	private List<String> adminName;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getAdminName() {
		return adminName;
	}

	public void setAdminName(List<String> adminName) {
		this.adminName = adminName;
	}

	@Override
	public String toString() {
		return "LabPojo{" +
				"address='" + address + '\'' +
				", adminName=" + adminName +
				"} " + super.toString();
	}
}
