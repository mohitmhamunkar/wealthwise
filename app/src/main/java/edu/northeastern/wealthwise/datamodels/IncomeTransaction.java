package edu.northeastern.wealthwise.datamodels;

import java.time.LocalDate;

/**
 * Income txn data model.
 */
public class IncomeTransaction {
    private String dateOfTransaction;
    private double incomeAmount;
    private String incomeCategory;
    private String accountCategory;
    private String note;

    public IncomeTransaction(String dateOfTransaction, double incomeAmount, String incomeCategory, String accountCategory, String note) {
        this.dateOfTransaction = dateOfTransaction;
        this.incomeAmount = incomeAmount;
        this.incomeCategory = incomeCategory;
        this.accountCategory = accountCategory;
        this.note = note;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public String getIncomeCategory() {
        return incomeCategory;
    }

    public String getAccountCategory() {
        return accountCategory;
    }

    public String getNote() {
        return note;
    }
}
