package edu.uni.labManagement.bean;

import java.util.Date;

public class RoutineMaintenanceDetail {
    private Long id;

    private Long deviceId;

    private Long routineMaintenanceId;

    private Boolean isFault;

    private String description;

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

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getRoutineMaintenanceId() {
        return routineMaintenanceId;
    }

    public void setRoutineMaintenanceId(Long routineMaintenanceId) {
        this.routineMaintenanceId = routineMaintenanceId;
    }

    public Boolean getIsFault() {
        return isFault;
    }

    public void setIsFault(Boolean isFault) {
        this.isFault = isFault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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