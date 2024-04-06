/**
 * This class represents a Debit Account that extends the Account class.
 */
package ru.kramskoi.accounts;

import ru.kramskoi.clients.Client;
import ru.kramskoi.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class DebitAccount extends Account {
    private BigDecimal moneyBuffer;

    /**
     * Constructor for creating a Debit Account.
     * @param accountId The unique identifier for the account.
     * @param client The client associated with the account.
     */
    public DebitAccount(UUID accountId, Client client) {
        super(accountId, client);
    }

    /**
     * Method to withdraw money from the account.
     * @param amount The amount to be withdrawn.
     * @throws TransactionException if the withdrawal amount exceeds the balance.
     */
    @Override
    public void withdraw(BigDecimal amount) throws TransactionException {
        super.withdraw(amount);
        if (amount.compareTo(balance) > 0) {
            throw new TransactionException("You cannot withdraw more than your balance");
        }
        balance = balance.subtract(amount);
    }

    /**
     * Method to calculate interest at the start of a new day.
     */
    @Override
    public void newDay() {
        moneyBuffer = moneyBuffer.add(balance.multiply(BigDecimal.valueOf(client.getBank().getInterestOnBalance() / 100)));
    }

    /**
     * Method to add interest to the balance at the start of a new month.
     */
    @Override
    public void newMonth() {
        balance = balance.add(moneyBuffer);
        moneyBuffer = BigDecimal.ZERO;;
        newDay();
    }
}