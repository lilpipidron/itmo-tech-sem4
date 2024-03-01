package ru.itmo.accounts;

import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.UUID;

public class DepositAccount extends Account {
    private final Time today;
    private final Time end;

    public DepositAccount(UUID accountId, Client client, Time start, Time end) {
        super(accountId, client);
        this.today = start;
        this.end = end;
    }

    @Override
    public void withdraw(BigDecimal amount) throws TransactionException {
        if (today.before(end))
            throw new TransactionException("You cannot withdraw money until the account's lifetime expires");
        super.withdraw(amount);
        if (amount.compareTo(balance) > 0)
            throw new TransactionException("You cannot withdraw more than your balance");
        balance = balance.subtract(amount);
    }
}
