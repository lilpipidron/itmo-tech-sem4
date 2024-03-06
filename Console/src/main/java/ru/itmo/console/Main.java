package ru.itmo.console;

import picocli.CommandLine;
import ru.itmo.console.ConsoleCommands.*;

@CommandLine.Command(name = "banks", subcommands = {CreateAccountCommand.class, CreateBankCommand.class, CreateClientCommand.class, DoReplenishmentCommand.class, DoTransferCommand.class, DoWithdrawalCommand.class, FindBankCommand.class, FindUserCommand.class})
public class Main {
}
