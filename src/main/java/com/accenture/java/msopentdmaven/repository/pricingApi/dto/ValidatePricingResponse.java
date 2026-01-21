package com.accenture.java.msopentdmaven.repository.pricingApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ValidatePricingResponse implements Serializable {

    private boolean isValid;

    private String notValidReason;

    public ValidatePricingResponse() {}

    public ValidatePricingResponse(boolean isValid, String notValidReason) {
        this.isValid = isValid;
        this.notValidReason = notValidReason;
    }

    @JsonProperty(value = "isValid")
    public boolean getIsValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getNotValidReason() {
        return notValidReason;
    }

    public void setNotValidReason(String notValidReason) {
        this.notValidReason = notValidReason;
    }
}
