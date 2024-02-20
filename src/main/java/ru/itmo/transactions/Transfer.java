package ru.itmo.transactions;

import java.math.BigDecimal;
import java.util.UUID;

public class Transfer implements Transaction {

    private final UUID senderId;
    private final UUID senderBank;
    private final UUID recipientId;
    private final UUID recipientBank;
    private final BigDecimal transferAmount;

    private TransactionStatus status = TransactionStatus.NotOccurred;

    public Transfer(UUID senderId, UUID senderBank, UUID recipientId, UUID recipientBank, BigDecimal transferAmount) {
        this.senderId = senderId;
        this.senderBank = senderBank;
        this.recipientId = recipientId;
        this.recipientBank = recipientBank;
        this.transferAmount = transferAmount;
    }

    @Override
    public void execute() throws IllegalStateException {
        if (status == TransactionStatus.Occurred)
            throw new IllegalStateException("The transaction has already occurred");
        if (status == TransactionStatus.Canceled)
            throw new IllegalStateException("The transaction has already been canceled");
        status = TransactionStatus.Occurred;
        //TODO
    }

    @Override
    public void cancel() {
        if (status == TransactionStatus.NotOccurred)
            throw new IllegalStateException("The transaction has not occurred yet");
        if (status == TransactionStatus.Canceled)
            throw new IllegalStateException("The transaction has already been canceled");
        status = TransactionStatus.Canceled;
        //TODO
    }
}
