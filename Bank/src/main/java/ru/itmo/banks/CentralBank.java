/**
 * The CentralBank class represents a central bank that manages multiple banks.
 */
package ru.itmo.banks;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;
import ru.itmo.transactions.Replenishment;
import ru.itmo.transactions.Transaction;
import ru.itmo.transactions.Transfer;
import ru.itmo.transactions.Withdrawal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class CentralBank {
    private HashMap<UUID, Bank> banks;
    private static CentralBank instance;

    private CentralBank() {
        banks = new HashMap<UUID, Bank>();
    }

    public static CentralBank getInstance() {
        if (instance == null)
            instance = new CentralBank();
        return instance;
    }

    /**
     * Creates a new bank with the specified parameters.
     *
     * @param name               the name of the bank
     * @param interestOnBalance  the interest rate on account balance
     * @param creditCommission   the commission for credit operations
     * @param accountRestriction the restriction on account operations
     * @param depositInterests   the list of deposit interests
     * @return the newly created bank
     * @throws IllegalArgumentException if the parameters are invalid
     */
    public Bank createBank(String name, float interestOnBalance, BigDecimal creditCommission, BigDecimal accountRestriction, ArrayList<DepositAndInterest> depositInterests) throws IllegalArgumentException {
        if (findBankByName(name) != null)
            throw new IllegalArgumentException("Bank exists");
        Bank.BankBuilder bankBuilder = new Bank.BankBuilder();
        UUID id = UUID.randomUUID();
        Bank bank = bankBuilder.withId(id)
                .withName(name)
                .withInterestOnBalance(interestOnBalance)
                .withCreditCommission(creditCommission)
                .withAccountRestrictions(accountRestriction)
                .withDepositInterests(depositInterests)
                .build();

        banks.put(id, bank);
        return bank;
    }

    public Bank findBankByName(String name) {
        Bank bank = null;
        for (Map.Entry<UUID, Bank> entry : banks.entrySet()) {
            if (entry.getValue().getName().equals(name)) {
                UUID bankId = entry.getKey();
                bank = entry.getValue();
                break;
            }
        }
        return bank;
    }

    /**
     * Initiates a transfer transaction between accounts.
     *
     * @param senderAccount      the account initiating the transfer
     * @param sender             the client initiating the transfer
     * @param recipientBankId    the ID of the recipient bank
     * @param recipientId        the ID of the recipient client
     * @param recipientAccountId the ID of the recipient account
     * @param amount             the amount to transfer
     * @throws TransactionException if the transaction encounters an issue
     */
    public void transferTransaction(Account senderAccount, Client sender, UUID recipientBankId, UUID recipientId, UUID recipientAccountId, BigDecimal amount) throws TransactionException {
        Client recepientClient = banks.get(recipientBankId).findClient(recipientId);
        Account recipientAccount = banks.get(recipientBankId).getAccounts().get(recipientId).get(recipientAccountId);
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transfer(transactionId, senderAccount, recipientAccount, amount);
    }

    /**
     * Initiates a withdrawal transaction from an account.
     *
     * @param account the account for withdrawal
     * @param amount  the amount to withdraw
     * @throws TransactionException if the transaction encounters an issue
     */
    public void withdrawalTransaction(Account account, BigDecimal amount) throws TransactionException {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Withdrawal(transactionId, account, amount);
        transaction.execute();
    }

    /**
     * Initiates a replenishment transaction for an account.
     *
     * @param account the account for replenishment
     * @param amount  the amount to replenish
     * @throws TransactionException if the transaction encounters an issue
     */
    public void replenishmentTransaction(Account account, BigDecimal amount) throws TransactionException {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Replenishment(transactionId, account, amount);
        transaction.execute();
    }

    /**
     * Cancels a specific transaction associated with an account.
     *
     * @param account       the account related to the transaction
     * @param transactionId the ID of the transaction to cancel
     * @throws TransactionException if canceling the transaction fails
     */
    public void cancelTransaction(Account account, UUID transactionId) throws TransactionException {
        Transaction transaction = account.findTransactionById(transactionId);
        transaction.cancel();
    }

    /**
     * Notifies all banks about a specific date.
     *
     * @param today the date to notify banks about
     */
    public void notifyBanks(LocalDate today) {
        for (Map.Entry<UUID, Bank> entry : banks.entrySet()) {
            Bank bank = entry.getValue();
            bank.getNotify(today);
        }
    }
}