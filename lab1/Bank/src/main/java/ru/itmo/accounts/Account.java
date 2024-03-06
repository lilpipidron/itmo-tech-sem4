/**
 * This class represents an abstract Account with basic functionalities like replenishment, withdrawal, and transfer.
 */
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

    /**
     * Constructor for creating an Account object.
     *
     * @param accountId The unique identifier for the account.
     * @param client The client associated with the account.
     */
    public Account(UUID accountId, Client client) {
        this.accountId = accountId;
        this.client = client;
        this.transactions = new HashMap<>();
        this.balance = new BigDecimal(0);
    }

    /**
     * Method to add funds to the account balance.
     *
     * @param amount The amount to be added to the balance.
     * @throws TransactionException if the deposit amount is invalid.
     */
    public void replenishment(BigDecimal amount) throws TransactionException {
        if (client.getStatus() == ClientStatus.Dubious && client.getBank().getAccountRestrictions().compareTo(amount) < 0)
            throw new TransactionException("The deposit amount exceeds the allowable amount for a questionable account");
        if (amount.compareTo(new BigDecimal(0)) < 0)
            throw new TransactionException("You cannot deposit less than 0 from your account");
        balance = balance.add(amount);
    }

    /**
     * Method to transfer funds between accounts.
     *
     * @param amount The amount to be transferred.
     * @param role The role of the account in the transfer.
     * @throws TransactionException if the transfer fails.
     */
    public void transfer(BigDecimal amount, TransferRole role) throws TransactionException {
        if (role == TransferRole.Sender)
            this.withdraw(amount);
        else
            this.replenishment(amount);
    }

    /**
     * Method to add a new transaction to the account's transaction history.
     *
     * @param transactionId The unique identifier for the transaction.
     * @param transaction The transaction object to be added.
     */
    public void addNewTransaction(UUID transactionId, Transaction transaction) {
        transactions.put(transactionId, transaction);
    }

    /**
     * Method to find a transaction by its ID.
     *
     * @param transactionId The ID of the transaction to find.
     * @return The transaction object corresponding to the ID.
     */
    public Transaction findTransactionById(UUID transactionId) {
        return transactions.get(transactionId);
    }

    /**
     * Method to withdraw funds from the account balance.
     *
     * @param amount The amount to be withdrawn from the balance.
     * @throws TransactionException if the withdrawal amount is invalid.
     */
    public void withdraw(BigDecimal amount) throws TransactionException {
        if (client.getStatus() == ClientStatus.Dubious && client.getBank().getAccountRestrictions().compareTo(amount) < 0)
            throw new TransactionException("The withdrawal amount exceeds the allowable amount for a doubtful account");
        if (amount.compareTo(new BigDecimal(0)) < 0)
            throw new TransactionException("You cannot withdrawal less than 0 into your account");
    }

    /**
     * Method to subscribe the account as a subscriber to its associated bank.
     */
    @Override
    public void subscribe(){
        client.getBank().addSubscriber(this);
    }

    /**
     * Method to unsubscribe the account as a subscriber from its associated bank.
     */
    @Override
    public void unsubscribe(){
        client.getBank().deleteSubscriber(this);
    }

    /**
     * Abstract method representing actions to be taken at the start of a new day for the account.
     */
    public abstract void newDay();

    /**
     * Abstract method representing actions to be taken at the start of a new month for the account.
     */
    public abstract void newMonth();
}