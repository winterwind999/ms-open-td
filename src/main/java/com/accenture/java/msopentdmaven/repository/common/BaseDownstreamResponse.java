package com.accenture.java.msopentdmaven.repository.common;

import java.util.Map;

public class BaseDownstreamResponse {

    private int errorCode;

    private String errorMessage;

    private Map<String, Object> errorDetails;

    public BaseDownstreamResponse() {
    }

    public BaseDownstreamResponse(
            int errorCode,
            String errorMessage,
            Map<String, Object> errorDetails
    ) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, Object> getErrorDetails() {
        return errorDetails;
    }

}
