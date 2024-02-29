package ru.itmo.banks;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;
import ru.itmo.transactions.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CentralBank {
    @Getter
    private HashMap<UUID, Bank> banks;
    @Getter
    private HashMap<UUID, Transaction> transactions;

    public Bank createBank(String name, float interestOnBalance, float creditCommission, ArrayList<DepositAndInterest>
            depositInterests) throws IllegalArgumentException {
        Bank.BankBuilder bankBuilder = new Bank.BankBuilder();
        UUID id = UUID.randomUUID();
        Bank bank = bankBuilder.withId(id)
                .withName(name)
                .withInterestOnBalance(interestOnBalance)
                .withCreditCommission(creditCommission)
                .withDepositInterests(depositInterests)
                .build();

        banks.put(id, bank);
        return bank;
    }

    public void TransferTransaction(Account senderAccount,
                                    Client sender,
                                    UUID recipientBankId,
                                    UUID recipientId,
                                    UUID recipientAccountId) throws TransactionException {
        Client recepientClient = banks.get(recipientBankId)
                .findClient(recipientId);
        Account recepientAccount = banks.get(recipientBankId)
                .getAccounts()
                .get(recipientId)
                .get(recipientAccountId);
    }

    public void WithdrawalTransaction(Account transactionAccount,
                                      UUID recipientBankId,
                                      UUID recipientId,
                                      UUID recipientAccountId) throws TransactionException {

    }
}
