package edu.northeastern.wealthwise.datamodels;

/**
 * It represents Account type used for txn.
 */
public enum AccountCategory {
    CASH("Cash"),
    BANK_ACCOUNTS("Bank Accounts"),
    CARD("Card"),
    OTHER("Other");

    public final String label;

    AccountCategory(String label) {
        this.label = label;
    }
}
