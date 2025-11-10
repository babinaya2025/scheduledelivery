// src/main/java/com/ford/scheduledelivery/model/PO.java
package com.ford.scheduledelivery.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects; // For equals and hashCode

@Entity
@Table(name = "po_order")
public class PO {
    @Id
    private Integer orderId;
    private String eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

    private Integer userId;
    private Integer vendorId;
    private Date orderDate;
    private Double amountINR;
    private Double amountUSD;
    private String poStatus;
    private Integer trainerId;
    private String scheduleStatus;

    // @NoArgsConstructor
    public PO() {
    }

    // Manually defined constructor (replaces @AllArgsConstructor(exclude = "user"))
    public PO(Integer orderId, String eventId, Integer userId, Integer vendorId, Date orderDate,
              Double amountINR, Double amountUSD, String poStatus, Integer trainerId, String scheduleStatus) {
        this.orderId = orderId;
        this.eventId = eventId;
        this.userId = userId;
        this.vendorId = vendorId;
        this.orderDate = orderDate;
        this.amountINR = amountINR;
        this.amountUSD = amountUSD;
        this.poStatus = poStatus;
        this.trainerId = trainerId;
        this.scheduleStatus = scheduleStatus;
    }

    // @Getter
    public Integer getOrderId() {
        return orderId;
    }

    // @Setter
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    // @Getter
    public String getEventId() {
        return eventId;
    }

    // @Setter
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    // @Getter
    public User getUser() {
        return user;
    }

    // @Setter
    public void setUser(User user) {
        this.user = user;
    }

    // @Getter
    public Integer getUserId() {
        return userId;
    }

    // @Setter
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // @Getter
    public Integer getVendorId() {
        return vendorId;
    }

    // @Setter
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    // @Getter
    public Date getOrderDate() {
        return orderDate;
    }

    // @Setter
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    // @Getter
    public Double getAmountINR() {
        return amountINR;
    }

    // @Setter
    public void setAmountINR(Double amountINR) {
        this.amountINR = amountINR;
    }

    // @Getter
    public Double getAmountUSD() {
        return amountUSD;
    }

    // @Setter
    public void setAmountUSD(Double amountUSD) {
        this.amountUSD = amountUSD;
    }

    // @Getter
    public String getPoStatus() {
        return poStatus;
    }

    // @Setter
    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus;
    }

    // @Getter
    public Integer getTrainerId() {
        return trainerId;
    }

    // @Setter
    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    // @Getter
    public String getScheduleStatus() {
        return scheduleStatus;
    }

    // @Setter
    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PO po = (PO) o;
        return Objects.equals(orderId, po.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "PO{" +
                "orderId=" + orderId +
                ", eventId='" + eventId + '\'' +
                ", userId=" + userId +
                ", vendorId=" + vendorId +
                ", orderDate=" + orderDate +
                ", amountINR=" + amountINR +
                ", amountUSD=" + amountUSD +
                ", poStatus='" + poStatus + '\'' +
                ", trainerId=" + trainerId +
                ", scheduleStatus='" + scheduleStatus + '\'' +
                '}';
    }
}
