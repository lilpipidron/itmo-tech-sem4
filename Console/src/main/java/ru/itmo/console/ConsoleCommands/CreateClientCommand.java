/**
 * Command to create a new client.
 */
package ru.itmo.console.ConsoleCommands;

import picocli.CommandLine;
import ru.itmo.banks.Bank;
import ru.itmo.banks.CentralBank;
import ru.itmo.clients.Client;

import java.util.Scanner;

@CommandLine.Command(name = "--createClient", description = "Create new client")
public class CreateClientCommand implements Runnable {

    /**
     * Method to execute the command and create a new client.
     */
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        CentralBank centralBank = CentralBank.getInstance();

        System.out.println("Enter client's name:");
        String name = in.nextLine();

        System.out.println("Enter client's surname:");
        String surname = in.nextLine();

        System.out.println("Enter client's passport number:");
        String passport = in.nextLine();

        System.out.println("Enter client's address:");
        String address = in.nextLine();

        System.out.println("Enter bank name:");
        String bankName = in.nextLine();

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