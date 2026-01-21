package com.accenture.java.msopentdmaven.api;

public class MaturityDetails {

    private String accountName;

    private String accountNumber;

    public MaturityDetails() {
    }

    public MaturityDetails(String accountName, String accountNumber) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
