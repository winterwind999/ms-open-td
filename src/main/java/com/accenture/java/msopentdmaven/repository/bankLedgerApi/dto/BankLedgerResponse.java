package com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto;

public class BankLedgerResponse {
    private String accountNumber;

    public BankLedgerResponse() {
    }

    public BankLedgerResponse(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
