/**
 * This interface represents a transaction in the system.
 */
package ru.kramskoi.transactions;

import lombok.Getter;
import ru.kramskoi.exceptions.TransactionException;

import java.util.UUID;

public abstract class Transaction {
    @Getter
    protected UUID transactionId;
    protected TransactionStatus status;

    protected Transaction(UUID transactionId) {
        this.transactionId = transactionId;
        status = TransactionStatus.NOT_OCCURRED;
    }

    /**
     * Executes the transfer transaction by transferring the specified amount from the sender to the recipient.
     *
     * @throws TransactionException if the transaction has already occurred or been canceled.
     */
    public void execute() throws TransactionException {
        if (status == TransactionStatus.OCCURRED)
            throw new TransactionException("The transaction has already occurred");
        if (status == TransactionStatus.CANCELED)
            throw new TransactionException("The transaction has already been canceled");
    }

    /**
     * Cancels the transfer transaction by reversing the transfer from recipient to sender.
     *
     * @throws TransactionException if the transaction has not occurred yet or has already been canceled.
     */
    public void cancel() throws TransactionException {
        if (status == TransactionStatus.NOT_OCCURRED)
            throw new TransactionException("The transaction has not occurred yet");
        if (status == TransactionStatus.CANCELED)
            throw new TransactionException("The transaction has already been canceled");
    }
}