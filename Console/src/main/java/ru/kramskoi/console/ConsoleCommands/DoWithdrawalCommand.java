/**
 * Command to perform a withdrawal transaction.
 */
package ru.kramskoi.console.ConsoleCommands;

import picocli.CommandLine;
import ru.kramskoi.accounts.Account;
import ru.kramskoi.banks.Bank;
import ru.kramskoi.banks.CentralBank;
import ru.kramskoi.clients.Client;
import ru.kramskoi.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

@CommandLine.Command(name = "--withdraw", description = "Do withdrawal")
public class DoWithdrawalCommand implements Runnable {

    /**
     * Method to execute the command and perform a withdrawal transaction.
     */
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        CentralBank centralBank = CentralBank.getInstance();

        System.out.println("Enter client ID for whom the withdrawal will be done:");
        UUID clientId = UUID.fromString(in.nextLine());

        System.out.println("Enter transaction ID for the withdrawal:");
        UUID transactionId = UUID.fromString(in.nextLine());

        System.out.println("Enter amount of funds to be withdrawn:");
        BigDecimal amount = new BigDecimal(in.nextLine());

        System.out.println("Enter Execute/Cancel to proceed:");
        String type = in.nextLine();

        System.out.println("Enter bank name where the client's account is located:");
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
            switch (type) {
                case "Execute":
                    bank.withdrawTransaction(account, amount);
                    break;
                case "Cancel":
                    bank.cancelTransaction(account, transactionId);
                    break;
                default:
                    System.out.println("Invalid operation. Please choose Execute or Cancel.");
            }
        } catch (TransactionException e) {
            System.out.println(e);
        }
    }
}