package edu.uni.labManagement.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * Create by Administrator
 *
 * @author sola
 * @date 2019/06/04 10:11
 */
public class ExcelDevicePojo extends BaseRowModel {

	@ExcelProperty(value = "设备主键编号", index = 0)
	private String id;

	@ExcelProperty(value = "设备类别", index = 1)
	private String category;

	@ExcelProperty(value = "设备名称", index = 2)
	private String name;

	@ExcelProperty(value = "所属部门", index = 3)
	private String department;

	@ExcelProperty(value = "实验室名称", index = 4)
	private String labName;

	@ExcelProperty(value = "设备型号", index = 5)
	private String model;

	@ExcelProperty(value = "设备编号", index = 6)
	private String number;

	@ExcelProperty(value = "序列号", index = 7)
	private String serial;

	@ExcelProperty(value = "设备描述", index = 8)
	private String description;

	@ExcelProperty(value = "设备生产日期", index = 9)
	private String productDate;

	@ExcelProperty(value = "批次", index = 10)
	private String batch;

	@Override
	public String toString() {
		return "ExcelDevicePojo{" +
				"id='" + id + '\'' +
				", category='" + category + '\'' +
				", name='" + name + '\'' +
				", department='" + department + '\'' +
				", labName='" + labName + '\'' +
				", model='" + model + '\'' +
				", number='" + number + '\'' +
				", serial='" + serial + '\'' +
				", description='" + description + '\'' +
				", productDate='" + productDate + '\'' +
				"} " + super.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}
}
