package entities;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/*
Класс отражающий сущность категории доходов или расходов для конкретного пользователя
    id : UUID -- идентификатор категории
    userID : UUID -- идентификатор пользователя
    flowType : String -- тип расходов (доходы/расходы)
    name : String -- название категории

 */
public class Category implements FileAccess {

    private final UUID id;
    private final UUID userID;
    private final String flowType;
    private final String name;

    public Category(UUID userID, String flowType, String name) {
        this.id = UUID.randomUUID();
        this.userID = userID;
        this.flowType = flowType;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void writeToFile() {
        String path = "./resources/categories.txt";
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(id.toString() + "," + userID + "," + flowType + "," + name + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }

    }
}
