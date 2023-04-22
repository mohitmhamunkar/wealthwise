package edu.northeastern.wealthwise.datamodels;

import java.time.LocalDate;

/**
 * Income txn data model.
 */
public class IncomeTransaction {
    private LocalDate dateOfTransaction;
    private double incomeAmount;
    private IncomeCategory incomeCategory;
    private AccountCategory accountCategory;
    private String note;

    public IncomeTransaction(LocalDate dateOfTransaction, double incomeAmount, IncomeCategory incomeCategory, AccountCategory accountCategory, String note) {
        this.dateOfTransaction = dateOfTransaction;
        this.incomeAmount = incomeAmount;
        this.incomeCategory = incomeCategory;
        this.accountCategory = accountCategory;
        this.note = note;
    }

    public LocalDate getDateOfTransaction() {
        return dateOfTransaction;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public IncomeCategory getIncomeCategory() {
        return incomeCategory;
    }

    public AccountCategory getAccountCategory() {
        return accountCategory;
    }

    public String getNote() {
        return note;
    }
}
