package edu.uni.labManagement.bean;

import java.time.LocalDateTime;

public class DeviceModelSlaves {
    private Long materId;

    private Long slaveId;

    private Integer amount;

    private Long universityId;

    private LocalDateTime datetime;

    private Long byWho;

    private Byte deleted;

    public Long getMaterId() {
        return materId;
    }

    public void setMaterId(Long materId) {
        this.materId = materId;
    }

    public Long getSlaveId() {
        return slaveId;
    }

    public void setSlaveId(Long slaveId) {
        this.slaveId = slaveId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Long getByWho() {
        return byWho;
    }

    public void setByWho(Long byWho) {
        this.byWho = byWho;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }
}