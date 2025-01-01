package Services;

import entities.Category;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CategoriesManager {
    String flowType;
    String name;

    public CategoriesManager(String flowType, String name) {
        this.flowType = flowType;
        this.name = name;
    }

    public void createCategory(CategoriesManager categoriesManager) {
        Category newCategory = new Category(flowType, name);
        newCategory.writeToFile();
        System.out.println("Категория создана");
    }

    public UUID getCategoryId(CategoriesManager manager) {
        String path = "./resources/categories.txt";
        String line;
        List<String> user = null;
        UUID id = null;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.contains(flowType) && line.contains(name)) {
                    user = Arrays.asList(line.split(","));
                    id = UUID.fromString(user.get(0));
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

}
