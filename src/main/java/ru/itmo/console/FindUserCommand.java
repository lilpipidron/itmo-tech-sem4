package ru.itmo.console;

import picocli.CommandLine;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.clients.Client;

import java.util.UUID;

@CommandLine.Command(name = "--findUser", description = "Find user by id")
public class FindUserCommand implements Runnable {
    @CommandLine.Option(names = {"-i", "--id"}, required = true, description = "User id")
    private UUID id;
    @CommandLine.Option(names = {"-b", "--bank"}, required = true, description = "User bank")
    private String bankName;

    @Override
    public void run() {
        CentralBank centralBank = CentralBank.getInstance();

        Bank bank = centralBank.findBankByName(bankName);
        if (bank == null) {
            System.out.println("Bank doesn't exist");
            return;
        }
        Client client = bank.findClient(id);
        if (client == null) {
            System.out.println("Client doesn't exist, check id and try again");
        }
    }
}
