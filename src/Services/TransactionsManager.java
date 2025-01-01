package Services;

import entities.Transaction;

import java.util.Calendar;
import java.util.UUID;

public class TransactionsManager {

    public static UUID createTransaction(UUID userId, UUID categoryId, double amount, Calendar date) {
        Transaction transaction = new Transaction(userId, categoryId, amount, date);
        transaction.writeToFile();
        return transaction.getId();
    }
}
