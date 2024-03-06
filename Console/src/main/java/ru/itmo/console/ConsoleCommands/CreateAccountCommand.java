package ru.itmo.console.ConsoleCommands;

import picocli.CommandLine;
import ru.itmo.accounts.CreditAccount;
import ru.itmo.accounts.DebitAccount;
import ru.itmo.accounts.DepositAccount;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.clients.Client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@CommandLine.Command(name = "--createAccount", description = "Create new account")
public class CreateAccountCommand implements Runnable {
    @CommandLine.Option(names = {"-t", "--type"}, required = true, description = "Type of account (credit, debit, deposit)")
    private String type;

    @CommandLine.Option(names = {"-c", "--clientId"}, required = true, description = "Client ID for whom the account will be created")
    private UUID clientId;
    @CommandLine.Option(names = {"-b", "--bank"}, required = true, description = "Bank where the client will be created")
    private String bankName;

    @Override
    public void run() {
        CentralBank centralBank = CentralBank.getInstance();

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
                    BigDecimal balance = new BigDecimal(0); // Initial balance for deposit account
                    LocalDate start = LocalDate.now(); // Start date for deposit account
                    LocalDate end = start.plusYears(1); // End date for deposit account (1 year from start)
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