package ru.itmo.console;

import picocli.CommandLine;
import ru.itmo.accounts.Account;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.clients.Client;
import ru.itmo.exceptions.TransactionException;

import java.math.BigDecimal;
import java.util.UUID;

@CommandLine.Command(name = "--Replenishment", description = "Do a replenishment transaction")

public class DoReplenishmentCommand implements Runnable {
    @CommandLine.Option(names = {"-c", "--clientId"}, required = true, description = "Client ID for whom the replenishment will be done")
    private UUID clientId;

    @CommandLine.Option(names = {"-t", "--transactionId"}, description = "Transaction ID for the replenishment")
    private UUID transactionId;

    @CommandLine.Option(names = {"-a", "--amount"}, required = true, description = "Amount of funds to be added")
    private BigDecimal amount;

    @CommandLine.Option(names = {"-o", "--operation"}, required = true, description = "Execute/Cancel")
    private String type;

    @CommandLine.Option(names = {"-b", "--bank"}, required = true, description = "Bank where the client will be created")
    private String bankName;

    @CommandLine.Option(names = {"-i", "--id"}, required = true, description = "Account id")
    private UUID accountId;
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

        Account account = bank.findAccount(clientId, accountId);
        if (account == null) {
            System.out.println("Account not found, check and try again");
            return;
        }

        try {
            switch(type){
                case "Execute":
                    centralBank.replenishmentTransaction(account,amount);
                    break;
                case "Cancel":
                    centralBank.cancelTransaction(account, transactionId);
                    break;
            }
        } catch (TransactionException e) {
            System.out.println(e);
        }
    }

}
