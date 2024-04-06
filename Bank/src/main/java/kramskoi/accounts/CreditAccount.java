/**
 * This class represents a Credit Account that extends the Account class.
 */
package ru.kramskoi.accounts;

import ru.kramskoi.clients.Client;
import ru.kramskoi.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class CreditAccount extends Account {

    /**
     * Constructor for creating a Credit Account.
     * @param accountId The unique identifier for the account.
     * @param client The client associated with the account.
     */
    public CreditAccount(UUID accountId, Client client) {
        super(accountId, client);
    }

    /**
     * Method to withdraw funds from the account.
     * @param amount The amount to be withdrawn.
     * @throws TransactionException If the transaction encounters an issue.
     */
    @Override
    public void withdraw(BigDecimal amount) throws TransactionException {
        super.withdraw(amount);
        balance = balance.subtract(amount);
    }

    /**
     * Method to handle operations at the start of a new day.
     * If the balance is negative, deducts credit commission from the balance.
     */
    @Override
    public void newDay() {
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            balance = balance.subtract(client.getBank().getCreditCommission());
    }

    /**
     * Method to handle operations at the start of a new month by calling newDay().
     */
    @Override
    public void newMonth(){
        newDay();
    }
}