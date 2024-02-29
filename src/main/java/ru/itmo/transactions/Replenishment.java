package ru.itmo.transactions;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class Replenishment implements Transaction {
    @Getter
    private final UUID transactionId;
    private final Account account;
    private final BigDecimal amount;

    private TransactionStatus status = TransactionStatus.NotOccurred;

    public Replenishment(UUID transactionId, Account account, BigDecimal amount) {
        this.transactionId = transactionId;
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() throws TransactionException {
        if (status == TransactionStatus.Occurred)
            throw new TransactionException("The transaction has already occurred");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        account.replenishment(amount);
    }

    @Override
    public void cancel() {
        if (status == TransactionStatus.NotOccurred)
            throw new TransactionException("The transaction has not occurred yet");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        account.withdraw(amount);
        status = TransactionStatus.Canceled;
    }
}
