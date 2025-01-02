package Services;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Statistics {

    // Реализовать возможность отображения общей суммы доходов и расходов, а также данных по каждой категории.
    //   - Выводить информацию о текущем состоянии бюджета для каждой категории, а также оставшийся лимит.
    //   - Поддерживать вывод информации в терминал или в файл.
//    Подсчет расходов и доходов:
//            - Разработать методы, подсчитывающие общие расходы и доходы, а также по категориям.
//   - Поддержать возможность подсчета по нескольким выбранным категориям. Если категория не найдена, уведомлять пользователя.

    //Подсчет общих доходов и расходов
    public static Double sumAllIncome(UUID userID) {
        String path = "./resources/transactions.txt";
        double income = 0;
        List<String> category = null;
        List<String> transaction = null;
        String line;
        String user = userID.toString();

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                transaction = Arrays.asList(line.split(","));

                if (transaction.get(1).equals(user)) {
                    category = CategoriesManager.getCategory(transaction.get(2));
                    if (category.get(2).equals("Доход")) {
                        income += Double.parseDouble(transaction.get(3));
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return income;
    }

    public static Double sumAllExpensses(UUID userID) {
        String path = "./resources/transactions.txt";
        double expensses = 0;
        List<String> category = null;
        List<String> transaction = null;
        String line;
        String user = userID.toString();

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                transaction = Arrays.asList(line.split(","));

                if (transaction.get(1).equals(user)) {
                    category = CategoriesManager.getCategory(transaction.get(2));
                    if (category.get(2).equals("Расход")) {
                        expensses += Double.parseDouble(transaction.get(3));
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return expensses;
    }

    public static Double sumByCategory(UUID userID,String flowType, String name) {
        String path = "./resources/transactions.txt";
        double value = 0;
        List<String> category = null;
        List<String> transaction = null;
        String line;
        String user = userID.toString();

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                transaction = Arrays.asList(line.split(","));

                if (transaction.get(1).equals(user)) {
                    category = CategoriesManager.getCategory(transaction.get(2));
                    if (category.get(2).equals(flowType) && category.get(3).equals(name)) {
                        value += Double.parseDouble(transaction.get(3));
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return value;
    }

    public static List<List<String>> getAllBudgetsCategories(UUID userID) {
        List<List<String>> allUserCategories = CategoriesManager.getAllUserCategories(userID);
        List<List<String>> allUserBudgets = BudgetManager.getAllUserBudgets(userID);
        List<List<String>> budgetsCategories = new ArrayList<>();
        List<String> budgetCategory = new ArrayList<>();
        int counter = 0;

        for (List<String> budget : allUserBudgets) {
            for (List<String> category : allUserCategories) {
                if (category.get(0).equals(budget.get(2))) {
                    budgetCategory.add(0, category.get(2));
                    budgetCategory.add(1, category.get(3));
                    budgetCategory.add(2, budget.get(3));
                    budgetCategory.add(3, budget.get(4));
                    budgetCategory.add(4, budget.get(5));
                    budgetCategory.add(5, budget.get(6));

                    budgetsCategories.add(budgetCategory);
                    budgetCategory = new ArrayList<>();
                }
            }

        }

        return budgetsCategories;
    }


}
