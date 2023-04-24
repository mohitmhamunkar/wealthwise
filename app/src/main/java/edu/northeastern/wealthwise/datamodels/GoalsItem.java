package edu.northeastern.wealthwise.datamodels;

public class GoalsItem {
    public String amount;
    public String category;

    public GoalsItem(String amount, String category) {
        this.amount = amount;
        this.category = category;
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
