package ru.itmo.console.ConsoleCommands;

import picocli.CommandLine;
import ru.itmo.accounts.Account;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;
@CommandLine.Command(name = "--transfer", description = "Do transfer")
public class DoTransferCommand implements Runnable {

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        CentralBank centralBank = CentralBank.getInstance();

        System.out.println("Enter transaction ID for the transfer:");
        UUID transactionId = UUID.fromString(in.nextLine());

        System.out.println("Enter amount of funds to be transferred:");
        BigDecimal amount = new BigDecimal(in.nextLine());

        System.out.println("Enter Execute/Cancel to proceed:");
        String type = in.nextLine();

        System.out.println("Enter bank name of the sender:");
        String bankNameSender = in.nextLine();

        System.out.println("Enter bank name of the recipient:");
        String bankNameRecipient = in.nextLine();

        System.out.println("Enter sender's account ID:");
        UUID senderId = UUID.fromString(in.nextLine());

        System.out.println("Enter recipient's account ID:");
        UUID recipientId = UUID.fromString(in.nextLine());

        System.out.println("Enter sender's client ID:");
        UUID senderClientId = UUID.fromString(in.nextLine());

        System.out.println("Enter recipient's client ID:");
        UUID recipientClientId = UUID.fromString(in.nextLine());

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
                    bankSender.transferTransaction(accountSender, clientSender, bankRecipient.getId(), recipientClientId, recipientId, amount);
                    break;
                case "Cancel":
                    bankRecipient.cancelTransaction(accountSender, transactionId);
                    break;
                default:
                    System.out.println("Invalid operation. Please choose Execute or Cancel.");
            }
        } catch (TransactionException e) {
            System.out.println(e);
        }
    }
}