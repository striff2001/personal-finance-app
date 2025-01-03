package Services;

import entities.Transaction;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class TransactionsManager {

    public static void createTransactionWithChecks(UUID userId, UUID categoryId, double amount, LocalDate date) {
        //Запись новой транзакции
        TransactionsManager.createTransaction(userId, categoryId, amount, date);

        //Проверка общего баланса
        boolean balance = TransactionsManager.checkBalance(userId);
        if (!balance) {
            System.out.println("Расходы превысили доходы!");
        }

        //Проверка лимита на бюджет
        String check = BudgetManager.checkBudgetLimit(userId, categoryId);
        if (check.startsWith("Бюджет исчерпан на")) {
            System.out.println(check);
        }
    }

    private static void createTransaction(UUID userId, UUID categoryId, double amount, LocalDate date) {
        Transaction transaction = new Transaction(userId, categoryId, amount, date);
        transaction.writeToFile();
        BudgetManager.updateBudgetCurrentAmount(userId, categoryId, amount);
        System.out.println("Транзакция создана");

        //return transaction.getId();
    }

    private static boolean checkBalance(UUID userId) {
        double income = Statistics.sumAllIncome(userId);
        double expense = Statistics.sumAllExpensses(userId);
        return income > expense;
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
