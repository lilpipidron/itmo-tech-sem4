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


    public Transfer(UUID transactionId, Account sender, Account recipient, BigDecimal amount) {
        this.transactionId = transactionId;
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

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

    @Override
    public void cancel() throws TransactionException {
        if (status == TransactionStatus.NotOccurred)
            throw new TransactionException("The transaction has not occurred yet");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        sender.transfer(amount, TransferRole.Recipient);
        recipient.transfer(amount,TransferRole.Sender);
        status = TransactionStatus.Canceled;
        sender.addNewTransaction(transactionId, this);
        recipient.addNewTransaction(transactionId, this);
    }
}
