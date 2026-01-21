package com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto;

public class BankLedgerDepositDetails {

    private String interestRate;

    private String depositAmount;

    private int termMonths;

    private String effectiveDate;

    private String expiryDate;

    public BankLedgerDepositDetails() {
    }

    public BankLedgerDepositDetails(
            String interestRate,
            String depositAmount,
            int term,
            String effectiveDate,
            String expiryDate
    ) {
        this.interestRate = interestRate;
        this.depositAmount = depositAmount;
        this.termMonths = term;
        this.effectiveDate = effectiveDate;
        this.expiryDate = expiryDate;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(int termMonths) {
        this.termMonths = termMonths;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
