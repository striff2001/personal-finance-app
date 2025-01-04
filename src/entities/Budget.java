package entities;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/*
Класс отражающий сущность бюджета, который пользователь устанавливает для категории расходов
    id : UUID -- идентификатор бюджета
    userId : UUID -- идентификатор пользователя
    categoryId : UUID -- идентификатор категории
    budgetLimit : double -- лимит бюджета
    currentAmount : double -- текущие траты бюджета
    startDate : LocalDate -- дата начала бюджета
    endDate : LocalDate -- дата окончания бюджета
*/
public class Budget implements FileAccess {

    private final UUID id;
    private final UUID userId;
    private final UUID categoryId;
    private double budgetLimit;
    private double currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Budget(UUID userId, UUID categoryId, double budgetLimit, double currentAmount, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.categoryId = categoryId;
        this.budgetLimit = budgetLimit;
        this.currentAmount = currentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public double getBudgetlimit() {
        return budgetLimit;
    }

    public void setBudgetlimit(double budgetlimit) {
        this.budgetLimit = budgetlimit;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public void writeToFile() {
        String path = "./resources/budgets.txt";
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(id.toString() + "," + userId + "," + categoryId + "," + budgetLimit + "," + currentAmount + "," + startDate + "," + endDate + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }
}
