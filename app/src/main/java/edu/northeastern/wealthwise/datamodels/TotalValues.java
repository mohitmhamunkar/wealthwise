package edu.northeastern.wealthwise.datamodels;

public class TotalValues {
    private double income;
    private double expense;
    private double total;

    public TotalValues() {
    }

    public TotalValues(double income, double expense, double total) {
        this.income = income;
        this.expense = expense;
        this.total = total;
    }

    public double getIncome() {
        return income;
    }

    public double getExpense() {
        return expense;
    }

    public double getTotal() {
        return total;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
