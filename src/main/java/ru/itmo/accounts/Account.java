package ru.itmo.accounts;

import lombok.Getter;
import ru.itmo.clients.Client;
import ru.itmo.clients.ClientStatus;
import ru.itmo.exceptions.TransactionException;
import ru.itmo.notifications.Subscriber;
import ru.itmo.transactions.Transaction;
import ru.itmo.transactions.TransferRole;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

@Getter
public abstract class Account extends Subscriber {
    protected final UUID accountId;
    protected BigDecimal balance;
    protected final Client client;
    protected HashMap<UUID, Transaction> transactions;

    public Account(UUID accountId, Client client) {
        this.accountId = accountId;
        this.client = client;
    }

    public void replenishment(BigDecimal amount) throws TransactionException {
        if (client.getStatus() == ClientStatus.Dubious && client.getBank().getAccountRestrictions().compareTo(amount) < 0)
            throw new TransactionException("The deposit amount exceeds the allowable amount for a questionable account");
        if (amount.compareTo(new BigDecimal(0)) < 0)
            throw new TransactionException("You cannot deposit less than 0 from your account");
        balance = balance.add(amount);
    }

    public void transfer(BigDecimal amount, TransferRole role) throws TransactionException {
        if (role == TransferRole.Sender)
            this.withdraw(amount);
        else
            this.replenishment(amount);
    }

    public void addNewTransaction(UUID transactionId, Transaction transaction) {
        transactions.put(transactionId, transaction);
    }

    public Transaction findTransactionById(UUID transactionId) {
        return transactions.get(transactionId);
    }

    public void withdraw(BigDecimal amount) throws TransactionException {
        if (client.getStatus() == ClientStatus.Dubious && client.getBank().getAccountRestrictions().compareTo(amount) < 0)
            throw new TransactionException("The withdrawal amount exceeds the allowable amount for a doubtful account");
        if (amount.compareTo(new BigDecimal(0)) < 0)
            throw new TransactionException("You cannot withdrawal less than 0 into your account");
    }
    @Override
    public void subscribe(){
        client.getBank().addSubscriber(this);
    }

    @Override
    public void unsubscribe(){
        client.getBank().deleteSubscriber(this);
    }

    public abstract void newDay();
    public abstract void newMonth();
}