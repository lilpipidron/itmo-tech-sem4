/**
 * Represents a transfer transaction between two accounts.
 */
package ru.itmo.transactions;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class Transfer implements Transaction {
    @Getter
    private final UUID transactionId;
    private final Account sender;
    private final Account recipient;
    private final BigDecimal amount;

    private TransactionStatus status = TransactionStatus.NotOccurred;

    /**
     * Constructs a new Transfer object with the specified parameters.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param sender The account sending the transfer.
     * @param recipient The account receiving the transfer.
     * @param amount The amount of money to be transferred.
     */
    public Transfer(UUID transactionId, Account sender, Account recipient, BigDecimal amount) {
        this.transactionId = transactionId;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    /**
     * Executes the transfer transaction by transferring the specified amount from the sender to the recipient.
     *
     * @throws TransactionException if the transaction has already occurred or been canceled.
     */
    @Override
    public void execute() throws TransactionException {
        if (status == TransactionStatus.Occurred)
            throw new TransactionException("The transaction has already occurred");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        sender.transfer(amount, TransferRole.Sender);
        recipient.transfer(amount, TransferRole.Recipient);
        status = TransactionStatus.Occurred;
        sender.addNewTransaction(transactionId, this);
        recipient.addNewTransaction(transactionId, this);
    }

    /**
     * Cancels the transfer transaction by reversing the transfer from recipient to sender.
     *
     * @throws TransactionException if the transaction has not occurred yet or has already been canceled.
     */
    @Override
    public void cancel() throws TransactionException {
        if (status == TransactionStatus.NotOccurred)
            throw new TransactionException("The transaction has not occurred yet");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        sender.transfer(amount, TransferRole.Recipient);
        recipient.transfer(amount, TransferRole.Sender);
        status = TransactionStatus.Canceled;
        sender.addNewTransaction(transactionId, this);
        recipient.addNewTransaction(transactionId, this);
    }
}