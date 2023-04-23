package edu.northeastern.wealthwise.datamodels;

public class StatsItem {
    private String percentage;
    private String category;
    private String amount;

    public StatsItem() {
    }

    public StatsItem(String percentage, String category, String amount) {
        this.percentage = percentage;
        this.category = category;
        this.amount = amount;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
