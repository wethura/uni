package edu.uni.labManagement.pojo;

import edu.uni.labManagement.bean.Lab;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/05/11 23:47
 */
public class LabPojo extends Lab {
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "LabPojo{" +
				"address='" + address + '\'' +
				"} " + super.toString();
	}
}
