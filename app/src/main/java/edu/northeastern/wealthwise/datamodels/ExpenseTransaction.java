package edu.northeastern.wealthwise.datamodels;

import java.time.LocalDate;

/**
 * Income txn data model.
 */
public class ExpenseTransaction {
    private LocalDate dateOfTransaction;
    private double expenseAmount;
    private ExpenseCategory expenseCategory;
    private AccountCategory accountCategory;
    private String note;

    public ExpenseTransaction(LocalDate dateOfTransaction, double expenseAmount, ExpenseCategory expenseCategory, AccountCategory accountCategory, String note) {
        this.dateOfTransaction = dateOfTransaction;
        this.expenseAmount = expenseAmount;
        this.expenseCategory = expenseCategory;
        this.accountCategory = accountCategory;
        this.note = note;
    }

    public LocalDate getDateOfTransaction() {
        return dateOfTransaction;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public AccountCategory getAccountCategory() {
        return accountCategory;
    }

    public String getNote() {
        return note;
    }
}
