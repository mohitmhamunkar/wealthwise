package edu.northeastern.wealthwise.datamodels;

import java.time.LocalDate;

/**
 * Income txn data model.
 */
public class ExpenseTransaction {
    private String dateOfTransaction;
    private double expenseAmount;
    private String expenseCategory;
    private String accountCategory;
    private String note;

    public ExpenseTransaction(String dateOfTransaction, double expenseAmount, String expenseCategory, String accountCategory, String note) {
        this.dateOfTransaction = dateOfTransaction;
        this.expenseAmount = expenseAmount;
        this.expenseCategory = expenseCategory;
        this.accountCategory = accountCategory;
        this.note = note;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public String getAccountCategory() {
        return accountCategory;
    }

    public String getNote() {
        return note;
    }
}
