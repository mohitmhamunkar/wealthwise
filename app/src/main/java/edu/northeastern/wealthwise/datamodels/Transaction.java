package edu.northeastern.wealthwise.datamodels;

public class Transaction {
    private String txnId;
    private String txnType;
    private String dateOfTransaction;
    private double amount;
    private String txnCategory;
    private String accountCategory;
    private String note;

    public Transaction() {
    }

    public Transaction(String txnType, String dateOfTransaction, double amount, String txnCategory, String accountCategory, String note) {
        this.txnType = txnType;
        this.dateOfTransaction = dateOfTransaction;
        this.amount = amount;
        this.txnCategory = txnCategory;
        this.accountCategory = accountCategory;
        this.note = note;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnType() {
        return txnType;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public double getAmount() {
        return amount;
    }

    public String getTxnCategory() {
        return txnCategory;
    }

    public String getAccountCategory() {
        return accountCategory;
    }

    public String getNote() {
        return note;
    }
}
