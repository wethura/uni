package edu.uni.labManagement.bean;

public class EquipmentModelBelongs {
    private Long materId;

    private Long slaveId;

    private Integer amount;

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
}