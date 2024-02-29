package ru.itmo.transactions;

import lombok.Getter;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

public class Transfer implements Transaction {


    @Getter
    private final UUID recipientId;
    @Getter
    private final UUID recipientBank;
    @Getter
    private final BigDecimal transferAmount;

    private TransactionStatus status = TransactionStatus.Occurred;

    public Transfer(UUID senderId, UUID senderBank, UUID recipientId, UUID recipientBank, BigDecimal transferAmount) {
        this.senderId = senderId;
        this.senderBank = senderBank;
        this.recipientId = recipientId;
        this.recipientBank = recipientBank;
        this.transferAmount = transferAmount;
    }

    @Override
    public void execute() throws TransactionException {
        if (status == TransactionStatus.Occurred)
            throw new TransactionException("The transaction has already occurred");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        status = TransactionStatus.Occurred;
        //TODO
    }

    @Override
    public void cancel() throws TransactionException {
        if (status == TransactionStatus.NotOccurred)
            throw new TransactionException("The transaction has not occurred yet");
        if (status == TransactionStatus.Canceled)
            throw new TransactionException("The transaction has already been canceled");
        status = TransactionStatus.Canceled;
        //TODO
    }
}
