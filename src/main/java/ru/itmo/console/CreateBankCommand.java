package ru.itmo.console;

import picocli.CommandLine;
import ru.itmo.banks.CentralBank;
import ru.itmo.banks.DepositAndInterest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

@CommandLine.Command(name = "--createBank", description = "Create new bank")
public class CreateBankCommand implements Runnable {
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        CentralBank centralBank = CentralBank.getInstance();

        System.out.println("Enter bank name");
        String name = in.nextLine();

        System.out.println("Enter interest on balance");
        float interestOnBalance = Float.parseFloat(in.nextLine());

        System.out.println("Enter credit commission");
        BigDecimal creditCommission = BigDecimal.valueOf(Float.parseFloat(in.nextLine()));

        System.out.println("Enter account restriction");
        BigDecimal accountRestriction = BigDecimal.valueOf(Float.parseFloat(in.nextLine()));

        ArrayList<DepositAndInterest> depositAndInterests = new ArrayList<DepositAndInterest>();
        System.out.println("Enter pairs depesit and interest, at the end of the widow enter end");

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
            centralBank.createBank(name,interestOnBalance,creditCommission,accountRestriction,depositAndInterests);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }
}
