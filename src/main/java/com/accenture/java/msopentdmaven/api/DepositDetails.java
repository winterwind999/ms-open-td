package com.accenture.java.msopentdmaven.api;

import com.accenture.java.msopentdmaven.exception.BadRequestException;
import com.accenture.java.msopentdmaven.repository.bankLedgerApi.dto.BankLedgerDepositDetails;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class DepositDetails {

    private String interestRate;

    private String depositAmount;

    private String term;

    private String effectiveDate;

    private String expiryDate;

    public DepositDetails(
            String interestRate,
            String depositAmount,
            String term,
            String effectiveDate,
            String expiryDate
    ) {
        this.interestRate = interestRate;
        this.depositAmount = depositAmount;
        this.term = term;
        this.effectiveDate = effectiveDate;
        this.expiryDate = expiryDate;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public String getTerm() {
        return term;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public BankLedgerDepositDetails generateBankLedgerDetailsDetails() {
        // ADDITIONAL FIX TERM OF AND TERM MONTHS
        int termMonths;

        if (this.term == null || this.term.isEmpty()) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), "Term cannot be null or empty", Map.of());
        }

        try {
            String[] parts = this.term.split("_");
            termMonths = Integer.parseInt(parts[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(), "Invalid term format: " + this.term, Map.of());
        }

        BankLedgerDepositDetails details = new BankLedgerDepositDetails();
        details.setInterestRate(interestRate);
        details.setDepositAmount(depositAmount);
        details.setTermMonths(termMonths);
        details.setEffectiveDate(effectiveDate);
        details.setExpiryDate(expiryDate);

        return details;
    }

    private int convertTermToIntMonths() {
        int numberOfMonthsInAYear = 12;
        String[] termArr = term.split("_");
        int termValueIndex = 0;
        int termTypeIndex = 1;
        int months = Integer.parseInt(termArr[termValueIndex]);

        if (termArr[termTypeIndex].toLowerCase().contains("year")) {
            months *= numberOfMonthsInAYear;
        }

        return months;
    }
}
