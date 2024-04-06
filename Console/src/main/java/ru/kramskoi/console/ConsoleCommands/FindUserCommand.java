/**
 * Command to find a user by ID.
 */
package ru.kramskoi.console.ConsoleCommands;

import picocli.CommandLine;
import ru.kramskoi.banks.Bank;
import ru.kramskoi.banks.CentralBank;
import ru.kramskoi.clients.Client;

import java.util.Scanner;
import java.util.UUID;

@CommandLine.Command(name = "--findUser", description = "Find user by id")
public class FindUserCommand implements Runnable {

    /**
     * Method to execute the command and find a user by ID.
     */
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        CentralBank centralBank = CentralBank.getInstance();

        System.out.println("Enter the user ID:");
        UUID id = UUID.fromString(in.nextLine());

        System.out.println("Enter the bank name:");
        String bankName = in.nextLine();

        Bank bank = centralBank.findBankByName(bankName);
        if (bank == null) {
            System.out.println("Bank doesn't exist");
            return;
        }

        Client client = bank.findClient(id);
        if (client == null) {
            System.out.println("Client doesn't exist, check ID and try again");
        }
    }
}