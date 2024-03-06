package ru.itmo.console.ConsoleCommands;

import picocli.CommandLine;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.clients.Client;

@CommandLine.Command(name = "--createClient", description = "Create new client")
public class CreateClientCommand implements Runnable {
    @CommandLine.Option(names = {"-n", "--name"}, required = true, description = "Client's name")
    private String name;

    @CommandLine.Option(names = {"-s", "--surname"}, required = true, description = "Client's surname")
    private String surname;

    @CommandLine.Option(names = {"-p", "--passport"}, description = "Client's passport number")
    private String passport;

    @CommandLine.Option(names = {"-a", "--address"}, description = "Client's address")
    private String address;

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
        try {
            Client client = bank.createClient(name, surname, passport, address);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}