package ru.itmo.transactions;

import ru.itmo.accounts.Account;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;

public class Withdrawal implements Transaction {
    private Account account;
    private BigDecimal amount;

    private TransactionStatus status = TransactionStatus.NotOccurred;

    public Withdrawal(Account account, BigDecimal amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() throws TransactionException {
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        account.withdraw(amount);
    }

    @Override
    public void cancel() {
        account.replenishment(amount);
    }
}
