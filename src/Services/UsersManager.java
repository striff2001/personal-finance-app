package Services;

import entities.User;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UsersManager {

    String login;
    String password;

    public UsersManager(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UUID createUser(UsersManager usersManager) {
        User newUser = new User(login, password);
        newUser.writeToFile();
        System.out.println("Пользователь создан");

        return newUser.getId();

        // Добавить проверку на уникальность логин + пароль

    }

    public UUID authorizeUser(UsersManager usersManager) {
        String path = "./resources/users.txt";
        String line;
        List<String> user = null;
        UUID id = null;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(login) && line.contains(password)) {
                    user = Arrays.asList(line.split(","));
                    id = UUID.fromString(user.get(0));
                    break;
                }
            }
            if (id == null) {
                System.out.println("Пользователь не найден");
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return id;
    }

    public static UUID authorizeUser(String login, String password) {
        String path = "./resources/users.txt";
        String line;
        List<String> user = null;
        UUID id = null;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(login) && line.contains(password)) {
                    user = Arrays.asList(line.split(","));
                    id = UUID.fromString(user.get(0));
                    break;
                }
            }
            if (id == null) {
                System.out.println("Пользователь не найден");
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return id;
    }




}
