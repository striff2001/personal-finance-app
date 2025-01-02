package Services;

import entities.Transaction;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class TransactionsManager {

    public static UUID createTransaction(UUID userId, UUID categoryId, double amount, LocalDate date) {
        Transaction transaction = new Transaction(userId, categoryId, amount, date);
        transaction.writeToFile();
        return transaction.getId();
    }

    public static UUID getCategoryId(UUID userId, String flowType, String name) {
        CategoriesManager manager = new CategoriesManager(userId, flowType, name);
        return manager.getCategoryId(manager);
    }

    public static List<String> getCategory(UUID id) {
        String path = "./resources/categories.txt";
        String line;
        List<String> category = null;
        String lineID = id.toString();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(lineID)) {
                    category = Arrays.asList(line.split(","));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return category;
    }

//    public static List<String> findUser(UUID id) {
//        String path = "./resources/users.txt";
//        String line;
//        List<String> user = null;
//        String lineID = id.toString();
//        try {
//            File file = new File(path);
//            Scanner scanner = new Scanner(file);
//            while (scanner.hasNextLine()) {
//                line = scanner.nextLine();
//                if (line.contains(lineID)) {
//                    user = Arrays.asList(line.split(","));
//                }
//            }
//            scanner.close();
//        } catch (IOException e) {
//            System.err.println("Ошибка при чтении файла: " + e.getMessage());
//        }
//        return user;
//    }


}
