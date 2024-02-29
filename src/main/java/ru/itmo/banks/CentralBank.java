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

        //TODO
    }

    public void withdrawalTransaction(Account account, BigDecimal amount) throws TransactionException {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Withdrawal(transactionId, account, amount);
        transaction.execute();
    }

    public void replenishmentTransaction(Account account, BigDecimal amount) throws TransactionException {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Replenishment(transactionId, account, amount);
        transaction.execute();
    }

    public void cancelTransaction(Account account, UUID transactionId) throws TransactionException {
        Transaction transaction = account.findTransactionById(transactionId);
        transaction.cancel();
    }
}
