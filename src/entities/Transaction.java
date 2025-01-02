package entities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Transaction implements FileAccess {

    private final UUID id;
    private final UUID userId;
    private final UUID categoryId;
    private final double amount;
    private final LocalDate date;

    public Transaction(UUID userId, UUID categoryId, double amount, LocalDate date) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.date = date;
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

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public void writeToFile() {
        String path = "./resources/transactions.txt";
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(id.toString() + "," + userId + "," + categoryId + "," + amount + "," + date + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }

    }

    @Override
    public List<String> findInFile(UUID id) {
        String path = "./resources/transactions.txt";
        String line;
        List<String> transaction = null;
        String lineID = id.toString();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(lineID)) {
                    transaction = Arrays.asList(line.split(","));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return transaction;
    }
}
