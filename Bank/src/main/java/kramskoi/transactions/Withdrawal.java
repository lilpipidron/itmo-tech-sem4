/**
 * Represents a withdrawal transaction from an account.
 */
package ru.kramskoi.transactions;

import lombok.Getter;
import ru.kramskoi.accounts.Account;
import ru.kramskoi.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class Withdrawal extends Transaction {
    @Getter
    private final UUID transactionId;
    private final Account account;
    private final BigDecimal amount;
    /**
     * Constructs a new Withdrawal transaction.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param account       The account from which the withdrawal is made.
     * @param amount        The amount to be withdrawn.
     */
    public Withdrawal(UUID transactionId, Account account, BigDecimal amount) {
        super(transactionId);
        this.transactionId = transactionId;
        this.account = account;
        this.amount = amount;
    }

    /**
     * Executes the transaction.
     *
     * @throws TransactionException if an error occurs during the execution of the transaction.
     */
    @Override
    public void execute() throws TransactionException {
        super.execute();
        account.withdraw(amount);
        status = TransactionStatus.OCCURRED;
        account.addNewTransaction(transactionId, this);
    }

    /**
     * Cancels the transaction.
     *
     * @throws TransactionException if an error occurs during the cancellation of the transaction.
     */
    @Override
    public void cancel() throws TransactionException {
        super.execute();
        account.replenishment(amount);
        status = TransactionStatus.CANCELED;
        account.addNewTransaction(transactionId, this);
    }
}