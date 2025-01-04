package Services;

import entities.Category;

import java.io.File;
import java.io.IOException;
import java.util.*;

/*
Класс для управления категориями
    userID : UUID -- идентификатор пользователя
    flowType : String -- тип расходов (доходы/расходы)
    name : String -- название категории
 */
public class CategoriesManager {
    UUID userID;
    String flowType;
    String name;

    public CategoriesManager(UUID userID, String flowType, String name) {
        this.userID = userID;
        this.flowType = flowType;
        this.name = name;
    }

    // Создание категории (запись в файл)
    public void createCategory(CategoriesManager categoriesManager) {
        Category newCategory = new Category(userID, flowType, name);
        newCategory.writeToFile();
        System.out.println("Категория создана");
    }

    // Получение идентификатора категории
    public UUID getCategoryId(CategoriesManager manager) {
        String path = "./resources/categories.txt";
        String line;
        List<String> category = null;
        UUID id = null;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(userID.toString()) && line.contains(flowType) && line.contains(name)) {
                    category = Arrays.asList(line.split(","));
                    id = UUID.fromString(category.get(0));
                    break;
                }
            }
            if (id == null) {
                System.out.println("Категория не найдена");
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return id;
    }

    // Получение полной информации о категории из файла (получение записи из файла по идентификатору (UUID) категории)
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

    // Получение полной информации о категории из файла (получение записи из файла по идентификатору (String) категории)
    public static List<String> getCategory(String id) {
        String path = "./resources/categories.txt";
        String line;
        List<String> category = null;
        //String lineID = id.toString();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(id)) {
                    category = Arrays.asList(line.split(","));
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return category;
    }

    // Получение всех категорий пользователя
    public static List<List<String>> getAllUserCategories(UUID userID) {
        String path = "./resources/categories.txt";
        String line;
        List<String> category = null;
        List<List<String>> allUserCategories = new ArrayList<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                category = Arrays.asList(line.split(","));
                if (category.get(1).equals(userID.toString())) {
                    allUserCategories.add(category);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
        return allUserCategories;
    }

}
