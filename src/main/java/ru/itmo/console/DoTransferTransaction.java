package ru.itmo.console;

import picocli.CommandLine;
import ru.itmo.accounts.Account;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

@CommandLine.Command(name = "--transfer", description = "Do transfer")
public class DoTransferTransaction implements Runnable {
    @CommandLine.Option(names = {"-t", "--transactionId"}, description = "Transaction ID for the replenishment")
    private UUID transactionId;

    @CommandLine.Option(names = {"-a", "--amount"}, description = "Amount of funds to be added")
    private BigDecimal amount;

    @CommandLine.Option(names = {"-o", "--operation"}, required = true, description = "Execute/Cancel")
    private String type;

    @CommandLine.Option(names = {"-f", "--from"}, required = true, description = "Bank where the sender will be created")
    private String bankNameSender;

    @CommandLine.Option(names = {"-z", "--to"}, description = "Bank where the recipient will be created")
    private String bankNameRecipient;

    @CommandLine.Option(names = {"-s", "--senderId"}, required = true, description = "Sender account id")
    private UUID senderId;

    @CommandLine.Option(names = {"-r", "--recipientId"}, description = "Recipient account id")
    private UUID recipientId;

    @CommandLine.Option(names = {"-w", "--senderClientId"}, description = "Sender client id")
    private UUID senderClientId;

    @CommandLine.Option(names = {"-e", "--recipientClientId"}, description = "Recipient client id")
    private UUID recipientClientId;

    @Override
    public void run() {
        CentralBank centralBank = CentralBank.getInstance();

        Bank bankSender = centralBank.findBankByName(bankNameSender);
        if (bankSender == null) {
            System.out.println("Bank sender doesn't exist");
            return;
        }


        Bank bankRecipient = centralBank.findBankByName(bankNameRecipient);
        if (bankRecipient == null) {
            System.out.println("Bank recipient doesn't exist");
            return;
        }

        Client clientSender = bankSender.findClient(senderClientId);

        if (clientSender == null) {
            System.out.println("Client sender not found.");
            return;
        }

        Account accountSender = bankSender.findAccount(senderClientId, senderId);
        if (accountSender == null) {
            System.out.println("Account sender not found, check and try again");
            return;
        }
        try {
            switch (type) {
                case "Execute":
                    centralBank.transferTransaction(accountSender, clientSender, bankRecipient.getId(), recipientClientId, recipientId, amount);
                    break;
                case "Cancel":
                    centralBank.cancelTransaction(accountSender, transactionId);
                    break;
            }
        } catch (TransactionException e) {
            System.out.println(e);
        }

    }
}
