package edu.uni.labManagement.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Device {
    private Long id;

    private Long deviceCategoryId;

    private Long departmentId;

    private Integer batch;

    private String number;

    private String model;

    private String name;

    private String serialNumber;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productDate;

    private Boolean isMaster;

    private Integer status;

    private Long universityId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date datetime;

    private Long byWho;

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceCategoryId() {
        return deviceCategoryId;
    }

    public void setDeviceCategoryId(Long deviceCategoryId) {
        this.deviceCategoryId = deviceCategoryId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Boolean getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(Boolean isMaster) {
        this.isMaster = isMaster;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Long getByWho() {
        return byWho;
    }

    public void setByWho(Long byWho) {
        this.byWho = byWho;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}