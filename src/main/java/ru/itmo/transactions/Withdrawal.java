package ru.itmo.transactions;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class Withdrawal implements Transaction {
    @Getter
    private final UUID transactionId;
    private final Account account;
    private final BigDecimal amount;

    private TransactionStatus status = TransactionStatus.NotOccurred;

    public Withdrawal(UUID transactionID, Account account, BigDecimal amount) {
        this.transactionId = transactionID;
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() throws TransactionException {
        if (status == TransactionStatus.Occurred)
            throw new TransactionException("The transaction has already occurred");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        account.withdraw(amount);
        status = TransactionStatus.Occurred;
        account.addNewTransaction(transactionId, this);
    }

    @Override
    public void cancel() {
        if (status == TransactionStatus.NotOccurred)
            throw new TransactionException("The transaction has not occurred yet");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        account.replenishment(amount);
        status = TransactionStatus.Canceled;
        account.addNewTransaction(transactionId, this);
    }
}
