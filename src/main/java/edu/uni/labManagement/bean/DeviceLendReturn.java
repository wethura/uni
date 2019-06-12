package edu.uni.labManagement.bean;

import java.util.Date;

public class DeviceLendReturn {
    private Long id;

    private Long userId;

    private Long fieldId;

    private Date planLendTime;

    private Date planReturnTime;

    private Integer approval;

    private Boolean returned;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Date getPlanLendTime() {
        return planLendTime;
    }

    public void setPlanLendTime(Date planLendTime) {
        this.planLendTime = planLendTime;
    }

    public Date getPlanReturnTime() {
        return planReturnTime;
    }

    public void setPlanReturnTime(Date planReturnTime) {
        this.planReturnTime = planReturnTime;
    }

    public Integer getApproval() {
        return approval;
    }

    public void setApproval(Integer approval) {
        this.approval = approval;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
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