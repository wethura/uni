package edu.uni.labManagement.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import edu.uni.labManagement.bean.Lab;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/02 20:19
 */
public class ExcelLabPojo extends BaseRowModel {

	@ExcelProperty(value = "实验室名称", index = 0)
	private String name;

	@ExcelProperty(value = "实验室英文名称", index = 1)
	private String ename;

	@ExcelProperty(value = "实验室描述", index = 2)
	private String description;

	@ExcelProperty(value = "实验室电话", index = 3)
	private String phone;

	@ExcelProperty(value = "主管账号", index = 4)
	private String admins;

	@ExcelProperty(value = "校区", index = 5)
	private String school;

	@ExcelProperty(value = "行政部门", index = 6)
	private String department;

	@ExcelProperty(value = "功能区", index = 7)
	private String schooolArea;

	@ExcelProperty(value = "地点名称", index = 8)
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdmins() {
		return admins;
	}

	public void setAdmins(String admins) {
		this.admins = admins;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSchooolArea() {
		return schooolArea;
	}

	public void setSchooolArea(String schooolArea) {
		this.schooolArea = schooolArea;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ExcelLabPojo{" +
				"name='" + name + '\'' +
				", ename='" + ename + '\'' +
				", description='" + description + '\'' +
				", phone='" + phone + '\'' +
				", admins='" + admins + '\'' +
				", school='" + school + '\'' +
				", department='" + department + '\'' +
				", schooolArea='" + schooolArea + '\'' +
				", address='" + address + '\'' +
				"} " + super.toString();
	}
}
