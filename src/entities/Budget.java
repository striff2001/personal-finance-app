package entities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Budget implements FileAccess {

    private final UUID id;
    private final UUID userId;
    private final UUID categoryId;
    private double budgetlimit;
    private double currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Budget(UUID userId, UUID categoryId, double budgetlimit, double currentAmount, LocalDate startDate, LocalDate endDate) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.categoryId = categoryId;
        this.budgetlimit = budgetlimit;
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
        return budgetlimit;
    }

    public void setBudgetlimit(double budgetlimit) {
        this.budgetlimit = budgetlimit;
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
            writer.write(id.toString() + "," + userId + "," + categoryId + "," + budgetlimit + "," + currentAmount + "," + startDate + "," + endDate + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }
    }

    @Override
    public List<String> findInFile(UUID id) {
        String path = "./resources/budgets.txt";
        String line;
        List<String> budget = null;
        String lineID = id.toString();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(lineID)) {
                    budget = Arrays.asList(line.split(","));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return budget;
    }
}
