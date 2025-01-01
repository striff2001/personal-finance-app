package entities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class User implements FileAccess {

    private final UUID id;
    private final String login;
    private String password;
    //private String firstName;
    //private String lastName;
    private final LocalDateTime creationDT;

    public User(String login, String password) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        //this.firstName = firstName;
        //this.lastName = lastName;
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

    @Override
    public List<String> findInFile(UUID id) {
        String path = "./resources/users.txt";
        String line;
        List<String> user = null;
        String lineID = id.toString();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(lineID)) {
                    user = Arrays.asList(line.split(","));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return user;




    }
}
