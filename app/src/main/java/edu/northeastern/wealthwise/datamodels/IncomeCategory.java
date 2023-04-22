package edu.northeastern.wealthwise.datamodels;

/**
 * Categories for income transactions.
 */
public enum IncomeCategory {
    ALLOWANCE("Allowance"),
    SALARY("Salary"),
    CASH("Cash"),
    BONUS("Bonus"),
    OTHER("Other");

    public final String label;

    IncomeCategory(String label) {
        this.label = label;
    }
}
