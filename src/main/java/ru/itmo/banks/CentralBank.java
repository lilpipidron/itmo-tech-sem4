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

    public Bank createBank(String name, float interestOnBalance, BigDecimal creditCommission, BigDecimal accountRestriction, ArrayList<DepositAndInterest>
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

    public void transferTransaction(Account senderAccount, Client sender, UUID recipientBankId, UUID recipientId, UUID recipientAccountId, BigDecimal amount) throws TransactionException {
        Client recepientClient = banks.get(recipientBankId)
                .findClient(recipientId);
        Account recipientAccount = banks.get(recipientBankId)
                .getAccounts()
                .get(recipientId)
                .get(recipientAccountId);
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transfer(transactionId,senderAccount, recipientAccount, amount);
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
    public void notifyBanks(LocalDate today) {
        for (Map.Entry<UUID, Bank> entry : banks.entrySet()) {
            Bank bank = entry.getValue();
            bank.getNotify(today);
        }
    }
}
