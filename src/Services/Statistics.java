package Services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Statistics {
    //Подсчет общих доходов и расходов
    protected static Double sumAllIncome(UUID userID) {
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

    protected static Double sumAllExpensses(UUID userID) {
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

    public static void printIncomeAndExpenses(UUID userID) {
        double income = Statistics.sumAllIncome(userID);
        double expensses = Statistics.sumAllExpensses(userID);
        System.out.println("Общий доход: " + income);
        System.out.println("Общий расход: " + expensses);
        System.out.println("Разница: " + (income - expensses));
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
                    } else {
                        System.out.println("Категория не найдена");
                    }
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return value;
    }

    private static List<List<String>> getAllBudgetsCategories(UUID userID) {
        List<List<String>> allUserCategories = CategoriesManager.getAllUserCategories(userID);
        List<List<String>> allUserBudgets = BudgetManager.getAllUserBudgets(userID);
        List<List<String>> budgetsCategories = new ArrayList<>();
        List<String> budgetCategory = new ArrayList<>();
        double limit = 0;

        for (List<String> budget : allUserBudgets) {
            for (List<String> category : allUserCategories) {
                if (category.get(0).equals(budget.get(2))) {
                    //budgetCategory.add(0, category.get(2));
                    budgetCategory.add(0, category.get(3));
                    budgetCategory.add(1, budget.get(3));
                    budgetCategory.add(2, budget.get(4));

                    // Делаем расчет остатка лимита
                    limit = Double.parseDouble(budget.get(3)) - Double.parseDouble(budget.get(4));
                    budgetCategory.add(3, String.valueOf(limit));

                    budgetCategory.add(4, budget.get(5));
                    budgetCategory.add(5, budget.get(6));

                    budgetsCategories.add(budgetCategory);
                    budgetCategory = new ArrayList<>();
                }
            }

        }

        return budgetsCategories;
    }

    public static void printAllBudgetsCategories(UUID userID) {
        List<List<String>> budgetsCategories = Statistics.getAllBudgetsCategories(userID);
        System.out.println("| Категория | Лимит | Текущий расход | Остаток лимита | Дата начала | Дата конца |");
        System.out.println("|-----------|-------|----------------|----------------|-------------|------------|");
        for (List<String> budgetCategory : budgetsCategories) {
            System.out.println("| " + budgetCategory.get(0) + " | " + budgetCategory.get(1) + " | " + budgetCategory.get(2) + " | " + budgetCategory.get(3) + " | " + budgetCategory.get(4) + " | " + budgetCategory.get(5) + " |");
        }
    }

    public static void saveAllBudgetsCategoriesToFile(UUID userID, String name) {
        List<List<String>> budgetsCategories = Statistics.getAllBudgetsCategories(userID);
        String path = "./output/" + name + ".txt";
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write("Категория,Лимит,Текущий расход,Остаток лимита,Дата начала,Дата конца\n");
            for (List<String> budgetCategory : budgetsCategories) {
                writer.write(budgetCategory.get(0) + "," + budgetCategory.get(1) + "," + budgetCategory.get(2) + "," + budgetCategory.get(3) + "," + budgetCategory.get(4) + "," + budgetCategory.get(5) + "\n");
            }
            System.out.println("Отчет выгружен в файл " + name + ".txt");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении в файл: " + e.getMessage());
        }

    }


}
