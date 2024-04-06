/**
 * Command to create a new bank.
 */
package ru.kramskoi.console.ConsoleCommands;

import picocli.CommandLine;
import ru.kramskoi.banks.CentralBank;
import ru.kramskoi.banks.DepositAndInterest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

@CommandLine.Command(name = "--createBank", description = "Create new bank")
public class CreateBankCommand implements Runnable {

    /**
     * Method to execute the command and create a new bank.
     */
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        CentralBank centralBank = CentralBank.getInstance();

        System.out.println("Enter bank name:");
        String name = in.nextLine();

        System.out.println("Enter interest on balance:");
        float interestOnBalance = Float.parseFloat(in.nextLine());

        System.out.println("Enter credit commission:");
        BigDecimal creditCommission = new BigDecimal(in.nextLine());

        System.out.println("Enter account restriction:");
        BigDecimal accountRestriction = new BigDecimal(in.nextLine());

        ArrayList<DepositAndInterest> depositAndInterests = new ArrayList<>();
        System.out.println("Enter pairs of deposit and interest. Enter 'end' to finish.");

        while (true) {
            String input = in.nextLine();
            if (input.equals("end")) {
                break;
            }

            String[] tokens = input.split(" ");
            if (tokens.length != 2) {
                System.out.println("Invalid input format. Please enter deposit and interest separated by a space.");
                continue;
            }

            try {
                BigDecimal deposit = new BigDecimal(tokens[0]);
                float interest = Float.parseFloat(tokens[1]);
                depositAndInterests.add(new DepositAndInterest(deposit, interest));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter valid numbers for deposit and interest.");
            }
        }

        try {
            centralBank.createBank(name, interestOnBalance, creditCommission, accountRestriction, depositAndInterests);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}