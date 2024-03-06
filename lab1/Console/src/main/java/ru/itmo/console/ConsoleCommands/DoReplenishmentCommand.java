/**
 * Command to perform a replenishment transaction.
 */
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

@CommandLine.Command(name = "--replenishment", description = "Do a replenishment transaction")
public class DoReplenishmentCommand implements Runnable {

    /**
     * Method to execute the command and perform a replenishment transaction.
     */
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        CentralBank centralBank = CentralBank.getInstance();

        System.out.println("Enter client ID for whom the replenishment will be done:");
        UUID clientId = UUID.fromString(in.nextLine());

        System.out.println("Enter transaction ID for the replenishment:");
        UUID transactionId = UUID.fromString(in.nextLine());

        System.out.println("Enter amount of funds to be added:");
        BigDecimal amount = new BigDecimal(in.nextLine());

        System.out.println("Enter Execute/Cancel to proceed:");
        String type = in.nextLine();

        System.out.println("Enter bank name:");
        String bankName = in.nextLine();

        System.out.println("Enter account ID:");
        UUID accountId = UUID.fromString(in.nextLine());

        Bank bank = centralBank.findBankByName(bankName);
        if (bank == null) {
            System.out.println("Bank doesn't exist");
            return;
        }

        Client client = bank.findClient(clientId);

        if (client == null) {
            System.out.println("Client not found. Please create the client first.");
            return;
        }

        Account account = bank.findAccount(clientId, accountId);
        if (account == null) {
            System.out.println("Account not found, check and try again");
            return;
        }

        try {
            switch(type){
                case "Execute":
                    centralBank.replenishmentTransaction(account, amount);
                    break;
                case "Cancel":
                    centralBank.cancelTransaction(account, transactionId);
                    break;
                default:
                    System.out.println("Invalid operation. Please choose Execute or Cancel.");
            }
        } catch (TransactionException e) {
            System.out.println(e);
        }
    }
}