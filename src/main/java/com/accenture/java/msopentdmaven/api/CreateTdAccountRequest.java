package com.accenture.java.msopentdmaven.api;

public class CreateTdAccountRequest {

    private String productCode;

    private DepositDetails depositDetails;

    private MaturityDetails maturityDetails;

    public CreateTdAccountRequest(
            String productCode,
            DepositDetails depositDetails,
            MaturityDetails maturityDetails
    ) {
        this.productCode = productCode;
        this.depositDetails = depositDetails;
        this.maturityDetails = maturityDetails;
    }

    public String getProductCode() {
        return productCode;
    }

    public DepositDetails getDepositDetails() {
        return depositDetails;
    }

    public MaturityDetails getMaturityDetails() {
        return maturityDetails;
    }
}
