package com.accenture.java.msopentdmaven.repository.pricingApi.dto;

public class ValidatePricingRequest {
    private String productId;

    private String interestRate;

    private String term;

    public ValidatePricingRequest(String productId, String interestRate, String term) {
        this.productId = productId;
        this.interestRate = interestRate;
        this.term = term;
    }

    public String getProductId() {
        return productId;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public String getTerm() {
        return term;
    }
}
