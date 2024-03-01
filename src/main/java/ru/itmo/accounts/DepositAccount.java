/**
 * Represents a Deposit Account that extends the Account class.
 */
package ru.itmo.accounts;

import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class DepositAccount extends Account {
    private LocalDate today;
    private final LocalDate end;
    private BigDecimal moneyBuffer;

    private final float percent;

    /**
     * Constructor for creating a Deposit Account.
     *
     * @param accountId the unique identifier of the account
     * @param client the client associated with the account
     * @param balance the initial balance of the account
     * @param start the start date of the account
     * @param end the end date of the account
     * @param percent the interest rate percentage for the account
     */
    public DepositAccount(UUID accountId, Client client, BigDecimal balance, LocalDate start, LocalDate end, float percent) {
        super(accountId, client);
        super.balance = balance;
        this.today = start;
        this.end = end;
        this.percent = percent;
    }

    /**
     * Withdraws a specified amount from the account.
     *
     * @param amount the amount to be withdrawn
     * @throws TransactionException if withdrawal conditions are not met
     */
    @Override
    public void withdraw(BigDecimal amount) throws TransactionException {
        if (today.isBefore(end))
            throw new TransactionException("You cannot withdraw money until the account's lifetime expires");
        super.withdraw(amount);
        if (amount.compareTo(balance) > 0)
            throw new TransactionException("You cannot withdraw more than your balance");
        balance = balance.subtract(amount);
    }

    /**
     * Updates account information for a new day.
     */
    @Override
    public void newDay() {
        today = today.plusDays(1);
        moneyBuffer = moneyBuffer.add(balance.multiply(new BigDecimal(percent / 100)));
    }

    /**
     * Updates account information for a new month by calling newDay().
     */
    @Override
    public void newMonth() {
        newDay();
    }
}