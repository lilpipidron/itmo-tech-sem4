/**
 * Command to create a new account.
 */
package ru.kramskoi.console.ConsoleCommands;

import picocli.CommandLine;
import ru.kramskoi.accounts.CreditAccount;
import ru.kramskoi.accounts.DebitAccount;
import ru.kramskoi.accounts.DepositAccount;
import ru.kramskoi.banks.Bank;
import ru.kramskoi.banks.CentralBank;
import ru.kramskoi.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

@CommandLine.Command(name = "--createAccount", description = "Create new account")
public class CreateAccountCommand implements Runnable {

    /**
     * Method to execute the command and create a new account.
     */
    @Override
    public void run() {
        CentralBank centralBank = CentralBank.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the type of account (credit, debit, deposit):");
        String type = scanner.nextLine();

        System.out.println("Enter the client ID for whom the account will be created:");
        UUID clientId = UUID.fromString(scanner.nextLine());

        System.out.println("Enter the bank where the client will be created:");
        String bankName = scanner.nextLine();

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

        try {
            switch (type) {
                case "credit":
                    CreditAccount creditAccount = bank.createCreditAccount(clientId, client);
                    break;
                case "debit":
                    DebitAccount debitAccount = bank.createDebitAccount(clientId, client);
                    break;
                case "deposit":
                    BigDecimal balance = new BigDecimal(0);
                    LocalDate start = LocalDate.now();
                    LocalDate end = start.plusYears(1);
                    DepositAccount depositAccount = bank.createDepositAccount(clientId, client, balance, start, end);
                    break;
                default:
                    System.out.println("Invalid account type. Please choose from credit, debit, or deposit.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}