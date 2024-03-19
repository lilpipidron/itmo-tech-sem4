/**
 * Represents a transfer transaction between two accounts.
 */
package ru.itmo.transactions;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class Transfer extends Transaction {
    @Getter
    private final UUID transactionId;
    private final Account sender;
    private final Account recipient;
    private final BigDecimal amount;

    /**
     * Constructs a new Transfer object with the specified parameters.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param sender        The account sending the transfer.
     * @param recipient     The account receiving the transfer.
     * @param amount        The amount of money to be transferred.
     */
    public Transfer(UUID transactionId, Account sender, Account recipient, BigDecimal amount) {
        super(transactionId);
        this.transactionId = transactionId;
        this.sender = sender;
        this.recipient = recipient;
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
        sender.transfer(amount, TransferRole.SENDER);
        try {
            recipient.transfer(amount, TransferRole.RECIPIENT);
        } catch (TransactionException e) {
            sender.transfer(amount, TransferRole.RECIPIENT);
            throw e;
        }
        status = TransactionStatus.OCCURRED;
        sender.addNewTransaction(transactionId, this);
        recipient.addNewTransaction(transactionId, this);
    }

    /**
     * Cancels the transaction.
     *
     * @throws TransactionException if an error occurs during the cancellation of the transaction.
     */
    @Override
    public void cancel() throws TransactionException {
        super.cancel();
        sender.transfer(amount, TransferRole.RECIPIENT);
        try {
            recipient.transfer(amount, TransferRole.SENDER);
        } catch (TransactionException e) {
            sender.transfer(amount, TransferRole.SENDER);
            throw e;
        }
        status = TransactionStatus.CANCELED;
        sender.addNewTransaction(transactionId, this);
        recipient.addNewTransaction(transactionId, this);
    }
}