package com.accenture.java.msopentdmaven.exception;

import java.util.Map;

public class ApiErrorResponse {

    private int errorCode;

    private String errorMessage;

    private Map<String, Object> errorDetails;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(
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

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(Map<String, Object> errorDetails) {
        this.errorDetails = errorDetails;
    }

}
