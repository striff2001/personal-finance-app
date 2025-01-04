package entities;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

/*
Класс отражающий сущность пользователя
    id : UUID -- идентификатор пользователя
    login : String -- логин пользователя
    password : String -- пароль пользователя
    creationDT : LocalDateTime -- дата создания пользователя
 */
public class User implements FileAccess {

    private final UUID id;
    private final String login;
    private String password;
    private final LocalDateTime creationDT;

    public User(String login, String password) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.creationDT = LocalDateTime.now();
    }


    public UUID getId() {
        return id;
    }


    @Override
    public void writeToFile() {
        String path = "./resources/users.txt";
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(id.toString() + "," + login + "," + password + "," + creationDT + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }

    }
}
