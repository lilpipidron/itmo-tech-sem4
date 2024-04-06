/**
 * Command to find a bank by name.
 */
package ru.kramskoi.console.ConsoleCommands;

import picocli.CommandLine;
import ru.kramskoi.banks.Bank;
import ru.kramskoi.banks.CentralBank;

import java.util.Scanner;

@CommandLine.Command(name = "--findBank", description = "Find bank by name")
public class FindBankCommand implements Runnable {

    /**
     * Method to execute the command and find a bank by name.
     */
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        CentralBank centralBank = CentralBank.getInstance();

        System.out.println("Enter the bank name to search for:");
        String bankName = in.nextLine();

        Bank bank = centralBank.findBankByName(bankName);
        if (bank == null) {
            System.out.println("Bank doesn't exist");
        }
    }
}