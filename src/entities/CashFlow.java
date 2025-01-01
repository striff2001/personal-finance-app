package entities;

public enum CashFlow {

    INCOME ("Доход"),
    OUTCOME ("Расход");
    private final String type;

    CashFlow(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
