package com.accenture.java.msopentdmaven.api;

public class  CreateTdAccountResponse {

    private String accountNumber;

    public CreateTdAccountResponse() {
    }

    public CreateTdAccountResponse(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
