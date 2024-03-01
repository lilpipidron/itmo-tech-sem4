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

    public DepositAccount(UUID accountId, Client client, BigDecimal balance, LocalDate start, LocalDate end, float percent) {
        super(accountId, client);
        super.balance = balance;
        this.today = start;
        this.end = end;
        this.percent = percent;
    }

    @Override
    public void withdraw(BigDecimal amount) throws TransactionException {
        if (today.isBefore(end))
            throw new TransactionException("You cannot withdraw money until the account's lifetime expires");
        super.withdraw(amount);
        if (amount.compareTo(balance) > 0)
            throw new TransactionException("You cannot withdraw more than your balance");
        balance = balance.subtract(amount);
    }

    @Override
    public void newDay() {
        today = today.plusDays(1);
        moneyBuffer = moneyBuffer.add(balance.multiply(new BigDecimal(percent / 100)));
    }

    @Override
    public void newMonth() {

    }
}
