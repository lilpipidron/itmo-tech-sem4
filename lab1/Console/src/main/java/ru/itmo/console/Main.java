package ru.itmo.console;

import picocli.CommandLine;
import ru.itmo.console.ConsoleCommands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@CommandLine.Command(name = "banks", subcommands = {CreateAccountCommand.class, CreateBankCommand.class, CreateClientCommand.class, DoReplenishmentCommand.class, DoTransferCommand.class, DoWithdrawalCommand.class, FindBankCommand.class, FindUserCommand.class})
public class Main {
    private static final Map<String, Runnable> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put("--createAccount", new CreateAccountCommand());
        COMMANDS.put("--createBank", new CreateBankCommand());
        COMMANDS.put("--createClient", new CreateClientCommand());
        COMMANDS.put("--replenishment", new DoReplenishmentCommand());
        COMMANDS.put("--withdrawal", new DoWithdrawalCommand());
        COMMANDS.put("--transfer", new DoTransferCommand());
        COMMANDS.put("--findBank", new FindBankCommand());
        COMMANDS.put("--findUser", new FindUserCommand());
    }

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new Main());

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();

            Runnable command = COMMANDS.get(input);
            if (command != null) {
                command.run();
            } else {
                System.out.println("Unknown command: " + input);
            }
        }
    }
}