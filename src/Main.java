import Services.CategoriesManager;
import Services.UsersManager;
import entities.CashFlow;
import entities.Category;
import entities.User;

import java.util.List;
import java.util.UUID;

import static entities.CashFlow.INCOME;

public class Main {
    public static void main(String[] args) {

//        UsersManager manager = new UsersManager("login1", "password1");
//        List<String> user = manager.authorizeUser(manager);
//        System.out.println(user);
//
//        UsersManager manager1 = new UsersManager("login2", "password2");
//        manager1.createUser(manager1);

        CategoriesManager manager = new CategoriesManager("Расход", "Продукты");
        //manager.createCategory(manager);
        UUID id = manager.getCategoryId(manager);
        System.out.println(id);


    }
}