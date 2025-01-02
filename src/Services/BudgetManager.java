package Services;

import entities.Budget;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class BudgetManager {
    UUID userId;
    UUID categoryId;
    double budgetLimit;
    double currentAmount;
    LocalDate startDate;
    LocalDate endDate;

    public BudgetManager(UUID userId, UUID categoryId, double budgetLimit, double currentAmount, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.budgetLimit = budgetLimit;
        this.currentAmount = currentAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void createBudget(BudgetManager budgetManager) {
        Budget newBudget = new Budget(userId, categoryId, budgetLimit, currentAmount, startDate, endDate);
        newBudget.writeToFile();
        System.out.println("Бюджет создан");
    }


    public static boolean updateBudgetCurrentAmount(UUID userID, UUID categoryID, double value) {
        String path = "./resources/budgets.txt";
        List<String> lines = new ArrayList<>();
        boolean updated = false;
        String user = userID.toString();
        String category = categoryID.toString();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                if (elements[1].trim().equals(user) && elements[2].trim().equals(category)) {
                    try {
                        double currentAmount = Double.parseDouble(elements[4].trim());
                        elements[4] = String.valueOf(currentAmount + value); // Увеличиваем значение currentAmount
                        updated = true;
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка преобразования числа в строке: " + line);
                    }
                }
                lines.add(String.join(",", elements)); // Добавляем обновленную или оригинальную строку в список
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return false;
        }

        // Перезаписываем файл с обновленными данными
        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("Строка с указанными параметрами не найдена.");
        }

        return updated;
    }


    public static String checkBudgetLimit(UUID userId, String flowType, String categoryName) {
        String check = "";
        double limit = 0;
        double amount = 0;
        CategoriesManager manager = new CategoriesManager(userId, flowType, categoryName);
        UUID categoryId = manager.getCategoryId(manager);

        String path = "./resources/budgets.txt";
        String line;
        List<String> budget = null;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(userId.toString()) && line.contains(categoryId.toString())) {
                    budget = Arrays.asList(line.split(","));
                    limit = Double.parseDouble(budget.get(2));
                    amount = Double.parseDouble(budget.get(3));
                    break;
                }
            }
            if (budget == null) {
                check = "Бюджет не найден";
            }
            scanner.close();
        } catch (IOException e) {
            check = "Ошибка при чтении файла: " + e.getMessage();
        }

    if (limit <= amount) {
        check = "Бюджет исчерпан на " + (amount - limit);
    } else {
        check = "До конца лимита осталось " + (limit - amount);
    }

    return check;
    }

    public static List<List<String>> getAllUserBudgets(UUID userID) {
        String path = "./resources/budgets.txt";
        String line;
        List<String> budget = null;
        List<List<String>> allUserBudgets = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                budget = Arrays.asList(line.split(","));
                if (budget.get(1).equals(userID.toString())) {
                    allUserBudgets.add(budget);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return allUserBudgets;
    }

}
