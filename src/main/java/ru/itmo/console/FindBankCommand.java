package ru.itmo.console;

import picocli.CommandLine;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;

@CommandLine.Command(name = "--findBank", description = "Find bank by name")
public class FindBankCommand implements Runnable {
    @CommandLine.Option(names = {"-b", "--bank"}, required = true, description = "Bank where the client will be created")
    private String bankName;
    @Override
    public void run() {
        CentralBank centralBank = CentralBank.getInstance();

        Bank bank = centralBank.findBankByName(bankName);
        if (bank == null) {
            System.out.println("Bank doesn't exist");
        }
    }
}
