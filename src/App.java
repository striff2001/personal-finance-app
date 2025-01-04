import Services.UsersManager;
import Services.BudgetManager;
import Services.CategoriesManager;
import Services.TransactionsManager;
import Services.Statistics;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

// Класс в котором отражена работа с пользователем
// На основе выбора пользователя происходит обращение к сервисному уровню приложения (пакет Services
public class App {
    public static void startApp() {

        Scanner scanner = new Scanner(System.in);
        boolean auth = false;
        UUID userID = null;
        int choice = 0;

        while (true) {
            try {
                while (!auth) {
                    System.out.println("""
                            1. Войти
                            2. Зарегистрироваться
                            3. Выход
                            """);
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            System.out.println("Введите логин: ");
                            String login = scanner.nextLine();
                            System.out.println("Введите пароль: ");
                            String password = scanner.nextLine();

                            userID = UsersManager.authorizeUser(login, password);
                            if (userID != null) {
                                auth = true;
                                System.out.println(" ");
                                break;
                            } else {
                                System.out.println("Неверные логин или пароль");
                                System.out.println(" ");
                                break;
                            }
                        case 2:
                            System.out.println("Придумайте логин: ");
                            String newLogin = scanner.nextLine();
                            System.out.println("Придумайте пароль: ");
                            String newPassword = scanner.nextLine();

                            UsersManager newUser = new UsersManager(newLogin, newPassword);
                            userID = newUser.createUser(newUser);
                            if (userID != null) {
                                auth = true;
                                System.out.println(" ");
                                break;
                            } else {
                                System.out.println("Ошибка создания пользователя");
                                break;
                            }
                        case 3:
                            System.out.println("До свидания!");
                            System.exit(0);
                        default:
                            System.out.println("Неверная команда");
                    }
                }

                while (true) {
                    System.out.println("""
                            1. Просмотреть доходы-расходы
                            2. Просмотреть бюджет для категории
                            3. Сформировать отчет по всем бюджетам
                            4. Создать транзакцию
                            5. Создать бюджет
                            6. Создать категорию
                            7. Выход
                            """);
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            Statistics.printIncomeAndExpenses(userID);
                            System.out.println(" ");
                            break;
                        case 2:
                            // Бюджет всегда для Расхода, поэтому тут нет ввода
                            //System.out.println("Введите тип категории (Доход/Расход): ");
                            String flowType = "Расход";
                            System.out.println("Введите название категории: ");
                            String name = scanner.nextLine();
                            System.out.println(BudgetManager.checkBudgetLimit(userID, flowType, name));
                            System.out.println(" ");
                            break;
                        case 3:
                            System.out.println("Хотите вывести в консоль или сформировать файл?");
                            System.out.println("1. Вывести в консоль");
                            System.out.println("2. Сформировать файл");
                            int choice2 = scanner.nextInt();
                            scanner.nextLine();
                            if (choice2 == 1) {
                                Statistics.printAllBudgetsCategories(userID);
                                System.out.println(" ");
                            } else if (choice2 == 2) {
                                System.out.println("Введите название файла: ");
                                String fileName = scanner.nextLine();
                                Statistics.saveAllBudgetsCategoriesToFile(userID, fileName);
                            } else {
                                System.out.println("Неверная команда");
                            }
                            System.out.println(" ");
                            break;
                        case 4:
                            boolean adding = true;
                            while (adding) {
                                System.out.println("Введите тип категории (Доход/Расход): ");
                                String transactionFlowType = scanner.nextLine();
                                System.out.println("Введите название категории: ");
                                String transactionCategoryName = scanner.nextLine();
                                System.out.println("Введите сумму транзакции: ");
                                double transactionAmount = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Введите дату транзакции (гггг-мм-дд): ");
                                String date = scanner.nextLine();
                                LocalDate transactionDate = LocalDate.parse(date);

                                UUID transactionCategoryID = TransactionsManager.getCategoryId(userID, transactionFlowType, transactionCategoryName);
                                if (transactionCategoryID == null) {
                                    System.out.println(" ");
                                    break;
                                } else {
                                    TransactionsManager.createTransactionWithChecks(userID, transactionCategoryID, transactionAmount, transactionDate);
                                }

                                System.out.println("Хотите добавить ещё транзакцию?");
                                System.out.println("1. Да");
                                System.out.println("2. Нет");
                                int choice3 = scanner.nextInt();
                                scanner.nextLine();
                                if (choice3 == 2) {
                                    adding = false;
                                }
                                System.out.println(" ");
                            }

                            break;
                        case 5:
                            // Бюджет всегда для Расхода устанавливается
                            //System.out.println("Введите тип категории (Доход/Расход): ");
                            String budgetFlowType = "Расход";
                            System.out.println("Введите название категории: ");
                            String budgetCategoryName = scanner.nextLine();
                            System.out.println("Введите лимит трат для категории: ");
                            double budgetLimit = scanner.nextDouble();
                            System.out.println("Введите текущие траты по категории, если есть (если нет введите 0)");
                            double budgetCurrentAmount = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.println("Введите дату начала бюджета (гггг-мм-дд): ");
                            String startDate = scanner.nextLine();
                            System.out.println("Введите дату окончания бюджета (гггг-мм-дд): ");
                            String endDate = scanner.nextLine();

                            LocalDate budgetStartDate = LocalDate.parse(startDate);
                            LocalDate budgetEndDate = LocalDate.parse(endDate);

                            UUID budgetCategoryID = TransactionsManager.getCategoryId(userID, budgetFlowType, budgetCategoryName);

                            if (budgetCategoryID == null) {
                                System.out.println(" ");
                                break;
                            } else {
                                BudgetManager budgetManager = new BudgetManager(userID, budgetCategoryID, budgetLimit, budgetCurrentAmount, budgetStartDate, budgetEndDate);
                                budgetManager.createBudget(budgetManager);
                                System.out.println(" ");
                                break;
                            }
                        case 6:
                            System.out.println("Введите тип категории (Доход/Расход): ");
                            String categoryFlowType = scanner.nextLine();
                            System.out.println("Введите название категории: ");
                            String categoryName = scanner.nextLine();
                            CategoriesManager categoryManager = new CategoriesManager(userID, categoryFlowType, categoryName);
                            categoryManager.createCategory(categoryManager);
                            System.out.println(" ");
                            break;
                        case 7:
                            System.out.println("До свидания!");
                            System.exit(0);
                        default:
                            System.out.println("Неверная команда");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Введите цифру выбранного пункта.");
                scanner.nextLine();
            }
        }
    }
}
