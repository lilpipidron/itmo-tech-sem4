/**
 * Represents a replenishment transaction that adds funds to an account.
 */
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

    /**
     * Constructs a new Replenishment transaction with the specified parameters.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param account The account to which the funds will be added.
     * @param amount The amount of funds to be added to the account.
     */
    public Replenishment(UUID transactionId, Account account, BigDecimal amount) {
        this.transactionId = transactionId;
        this.account = account;
        this.amount = amount;
    }

    /**
     * Executes the replenishment transaction by adding the specified amount to the account.
     *
     * @throws TransactionException if the transaction has already occurred or been canceled.
     */
    @Override
    public void execute() throws TransactionException {
        if (status == TransactionStatus.Occurred)
            throw new TransactionException("The transaction has already occurred");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        account.replenishment(amount);
        status = TransactionStatus.Occurred;
        account.addNewTransaction(transactionId, this);
    }

    /**
     * Cancels the replenishment transaction by withdrawing the added funds from the account.
     *
     * @throws TransactionException if the transaction has not occurred yet or has already been canceled.
     */
    @Override
    public void cancel() {
        if (status == TransactionStatus.NotOccurred)
            throw new TransactionException("The transaction has not occurred yet");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        account.withdraw(amount);
        status = TransactionStatus.Canceled;
        account.addNewTransaction(transactionId, this);
    }
}