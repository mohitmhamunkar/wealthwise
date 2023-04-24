package edu.northeastern.wealthwise.datamodels;

public class GoalsItem {
    public String amount;
    public String category;
    public String categoryExpense;
    public boolean goalStatus;

    public GoalsItem() {
    }

    public GoalsItem(String amount, String category) {
        this.amount = amount;
        this.category = category;
    }

    public boolean isGoalStatus() {
        return goalStatus;
    }

    public void setGoalStatus(boolean goalStatus) {
        this.goalStatus = goalStatus;
    }

    public String getCategoryExpense() {
        return categoryExpense;
    }

    public void setCategoryExpense(String categoryExpense) {
        this.categoryExpense = categoryExpense;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
