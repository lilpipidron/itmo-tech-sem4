/**
 * Represents a replenishment transaction that adds funds to an account.
 */
package ru.itmo.transactions;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class Replenishment extends Transaction {
    @Getter
    private final UUID transactionId;
    private final Account account;
    private final BigDecimal amount;
    /**
     * Constructs a new Replenishment transaction with the specified parameters.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param account       The account to which the funds will be added.
     * @param amount        The amount of funds to be added to the account.
     */
    public Replenishment(UUID transactionId, Account account, BigDecimal amount) {
        super(transactionId);
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
        super.execute();
        account.replenishment(amount);
        status = TransactionStatus.OCCURRED;
        account.addNewTransaction(transactionId, this);
    }

    /**
     * Cancels the replenishment transaction by withdrawing the added funds from the account.
     *
     * @throws TransactionException if the transaction has not occurred yet or has already been canceled.
     */
    @Override
    public void cancel() throws TransactionException {
        super.cancel();
        account.withdraw(amount);
        status = TransactionStatus.CANCELED;
        account.addNewTransaction(transactionId, this);
    }
}