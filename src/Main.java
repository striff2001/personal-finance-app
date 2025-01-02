import Services.BudgetManager;
import Services.CategoriesManager;
import Services.Statistics;
import Services.UsersManager;
import entities.CashFlow;
import entities.Category;
import entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static entities.CashFlow.INCOME;

public class Main {
    public static void main(String[] args) {
        //BudgetManager manager = new BudgetManager(UUID.randomUUID(), UUID.randomUUID(), 1000, 0, LocalDate.now(), LocalDate.parse("2025-01-05"));
        //manager.createBudget(manager);

        List<List<String>> x=  Statistics.getAllBudgetsCategories(UUID.fromString("7b2c0dae-4c68-417c-ab06-fa6c8617fa25"));
        for (List<String> budgetCategory : x) {
            System.out.println(budgetCategory);
        }

        //BudgetManager.updateBudgetCurrentAmount(UUID.fromString("7b2c0dae-4c68-417c-ab06-fa6c8617fa25"), UUID.fromString("2c011fe3-8f18-414e-8103-1689ac9d7f06"), 500);
//        List<List<String>> x= BudgetManager.getAllUserBudgets(UUID.fromString("7b2c0dae-4c68-417c-ab06-fa6c8617fa25"));
//        for (List<String> budget : x) {
//            System.out.println(budget);
//        }

//        UsersManager manager = new UsersManager("login1", "password1");
//        List<String> user = manager.authorizeUser(manager);
//        System.out.println(user);
//
//        UsersManager manager1 = new UsersManager("login2", "password2");
//        manager1.createUser(manager1);




    }
}