package edu.northeastern.wealthwise.datamodels;

/**
 * Categories for expense transactions.
 */
public enum ExpenseCategory {
    FOOD("Food"),
    SOCIAL_LIFE("Social Life"),
    PETS("Pets"),
    TRANSPORT("Transport"),
    CULTURE("Culture"),
    HOUSEHOLD("Household"),
    APPAREL("Apparel"),
    BEAUTY("Beauty"),
    HEALTH("Health"),
    EDUCATION("Education"),
    GIFT("Gift"),
    OTHER("Other");

    public final String label;

    ExpenseCategory(String label) {
        this.label = label;
    }
}
