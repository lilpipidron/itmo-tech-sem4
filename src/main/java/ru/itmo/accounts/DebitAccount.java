package ru.itmo.accounts;

import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class DebitAccount extends Account {
    private BigDecimal moneyBuffer;

    public DebitAccount(UUID accountId, Client client) {
        super(accountId, client);
    }

    @Override
    public void withdraw(BigDecimal amount) throws TransactionException {
        super.withdraw(amount);
        if (amount.compareTo(balance) > 0) {
            throw new TransactionException("You cannot withdraw more than your balance");
        }
        balance = balance.subtract(amount);
    }
}