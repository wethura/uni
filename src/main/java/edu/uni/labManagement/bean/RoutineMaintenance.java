package edu.uni.labManagement.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RoutineMaintenance {
    private Long id;

    private Long labId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String remainOption;

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

    public Long getLabId() {
        return labId;
    }

    public void setLabId(Long labId) {
        this.labId = labId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemainOption() {
        return remainOption;
    }

    public void setRemainOption(String remainOption) {
        this.remainOption = remainOption == null ? null : remainOption.trim();
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

    @Override
    public String toString() {
        return "RoutineMaintenance{" +
                "id=" + id +
                ", labId=" + labId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", remainOption='" + remainOption + '\'' +
                ", universityId=" + universityId +
                ", datetime=" + datetime +
                ", byWho=" + byWho +
                ", deleted=" + deleted +
                '}';
    }
}