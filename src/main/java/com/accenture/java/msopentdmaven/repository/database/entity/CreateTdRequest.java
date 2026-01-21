package com.accenture.java.msopentdmaven.repository.database.entity;

import com.accenture.java.msopentdmaven.repository.database.type.CreateTdRequestStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "create_td_requests", schema = "td")
public class CreateTdRequest implements Serializable {

    private static final long serialVersionUID = -12345667788975L;

    @Id
    @Column(name = "correlationId", unique = true, nullable = false)
    private String correlationId;

    @Column(name = "requestPayload", nullable = false)
    private String requestPayloadStr;

    @Column(name = "productId")
    private String productId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "attempt_count", nullable = false)
    private Integer attemptCount;

    @Column(name = "created_datetime", nullable = false)
    private Timestamp createdDateTime;

    @Column(name = "updated_datetime", nullable = false)
    private Timestamp updatedDateTime;

    public CreateTdRequest(String correlationId, String requestPayloadStr) {
        this.correlationId = correlationId;
        this.requestPayloadStr = requestPayloadStr;
        this.status = CreateTdRequestStatus.INITIATED.name();
        this.productId = null;
        this.attemptCount = 1;
    }

    // FIX TASK 3
    public CreateTdRequest() {

    }

    @PrePersist
    protected void onCreate() {
        createdDateTime = new Timestamp(System.currentTimeMillis());
        updatedDateTime = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDateTime = new Timestamp(System.currentTimeMillis());
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getRequestPayloadStr() {
        return requestPayloadStr;
    }

    public void setRequestPayloadStr(String requestPayloadStr) {
        this.requestPayloadStr = requestPayloadStr;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Timestamp getCreatedDateTime() {
        return new Timestamp(createdDateTime.getTime());
    }

    public void setCreatedDateTime(Timestamp createdDateTime) {
        this.createdDateTime = new Timestamp(createdDateTime.getTime());
    }

    public Timestamp getUpdatedDateTime() {
        return new Timestamp(updatedDateTime.getTime());
    }

    public void setUpdatedDateTime(Timestamp updatedDateTime) {
        this.updatedDateTime = new Timestamp(updatedDateTime.getTime());
    }

    public void increaseAttemptCount() {
        this.attemptCount++;
    }
}
