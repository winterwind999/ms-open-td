package com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto;

import com.accenture.java.msopentdmaven.api.MaturityDetails;

public class BankLedgerRequest {

    private String productId;

    private BankLedgerDepositDetails termDepositDetails;

    private MaturityDetails termDepositMaturityDetails;

    public BankLedgerRequest() {
    }

    public BankLedgerRequest(
            String productId,
            BankLedgerDepositDetails termDepositDetails,
            MaturityDetails termDepositMaturityDetails
    ) {
        this.productId = productId;
        this.termDepositDetails = termDepositDetails;
        this.termDepositMaturityDetails = termDepositMaturityDetails;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BankLedgerDepositDetails getTermDepositDetails() {
        return termDepositDetails;
    }

    public void setTermDepositDetails(BankLedgerDepositDetails termDepositDetails) {
        this.termDepositDetails = termDepositDetails;
    }

    public MaturityDetails getTermDepositMaturityDetails() {
        return termDepositMaturityDetails;
    }

    public void setTermDepositMaturityDetails(MaturityDetails termDepositMaturityDetails) {
        this.termDepositMaturityDetails = termDepositMaturityDetails;
    }
}
