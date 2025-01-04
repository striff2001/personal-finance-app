package entities;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/*
Класс отражающий сущность транзакции пользователя
    id : UUID -- идентификатор транзакции
    userId : UUID -- идентификатор пользователя
    categoryId : UUID -- идентификатор категории
    amount : double -- сумма транзакции
    date : LocalDate -- дата транзакции
 */
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
}
