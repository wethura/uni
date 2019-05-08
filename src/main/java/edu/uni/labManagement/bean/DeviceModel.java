package edu.uni.labManagement.bean;

import java.util.Date;

public class DeviceModel {
    private Long id;

    private Long deviceCategoryId;

    private String version;

    private String name;

    private String producter;

    private String description;

    private Boolean isSlave;

    private Long universityId;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProducter() {
        return producter;
    }

    public void setProducter(String producter) {
        this.producter = producter == null ? null : producter.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getIsSlave() {
        return isSlave;
    }

    public void setIsSlave(Boolean isSlave) {
        this.isSlave = isSlave;
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