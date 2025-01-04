package entities;
// Класс отражающий два типа категорий "Расходы" и "Доходы"
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
