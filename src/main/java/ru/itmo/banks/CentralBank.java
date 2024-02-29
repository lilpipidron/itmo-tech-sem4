package ru.itmo.banks;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;
import ru.itmo.transactions.Replenishment;
import ru.itmo.transactions.Transaction;
import ru.itmo.transactions.Withdrawal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
public class CentralBank {
    private HashMap<UUID, Bank> banks;
    private HashMap<UUID, HashMap<UUID, Transaction>> transactions;

    public Bank createBank(String name, float interestOnBalance, float creditCommission, BigDecimal accountRestriction, ArrayList<DepositAndInterest>
            depositInterests) throws IllegalArgumentException {
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

    public void transferTransaction(Account senderAccount, Client sender, UUID recipientBankId, UUID recipientId, UUID recipientAccountId) throws TransactionException {
        Client recepientClient = banks.get(recipientBankId)
                .findClient(recipientId);
        Account recepientAccount = banks.get(recipientBankId)
                .getAccounts()
                .get(recipientId)
                .get(recipientAccountId);
    }

    public void withdrawalTransaction(Account account, BigDecimal amount) throws TransactionException {
        Transaction transaction = new Withdrawal(account, amount);
        transaction.execute();
        HashMap<UUID, Transaction> transactionsByAccount = transactions.get(account.getAccountId());
        UUID transactionId = UUID.randomUUID();
        transactionsByAccount.put(transactionId, transaction);
        transactions.put(account.getAccountId(), transactionsByAccount);
    }

    public void replenishmentTransaction(Account account, BigDecimal amount) throws TransactionException {
        Transaction transaction = new Replenishment(account, amount);
        transaction.execute();
        HashMap<UUID, Transaction> transactionsByAccount = transactions.get(account.getAccountId());
        UUID transactionId = UUID.randomUUID();
        transactionsByAccount.put(transactionId, transaction);
        transactions.put(account.getAccountId(), transactionsByAccount);
    }


    public void cancelTransaction(Account account, UUID transactionId) throws TransactionException {
        transactions.get(account.getAccountId()).get(transactionId).cancel();
    }
}
