package ru.itmo.accounts;

import lombok.Getter;
import ru.itmo.clients.Client;
import ru.itmo.clients.ClientStatus;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public abstract class Account {
    protected final UUID accountId;
    protected BigDecimal balance;

    protected final Client client;

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

    public void transfer(BigDecimal amount, UUID recipientId, UUID recipientBankId) {
//TODO
    }

    public abstract void withdraw(BigDecimal amount) throws TransactionException;
}