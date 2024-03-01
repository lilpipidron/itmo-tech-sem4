package ru.itmo.banks;

import lombok.Getter;
import ru.itmo.accounts.Account;
import ru.itmo.accounts.CreditAccount;
import ru.itmo.accounts.DebitAccount;
import ru.itmo.accounts.DepositAccount;
import ru.itmo.clients.Client;
import ru.itmo.notifications.Publisher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Getter
public class Bank extends Publisher {

    private final UUID id;
    private final String name;
    private float interestOnBalance;
    private BigDecimal creditCommission;
    private BigDecimal accountRestrictions;
    private ArrayList<DepositAndInterest> depositInterests;
    private HashMap<UUID, Client> clients;
    private HashMap<UUID, HashMap<UUID, Account>> accounts;

    private Bank(UUID id, String name, float interestOnBalance, BigDecimal creditCommission, BigDecimal accountRestrictions, ArrayList<DepositAndInterest> depositInterests) {
        this.id = id;
        this.name = name;
        this.interestOnBalance = interestOnBalance;
        this.creditCommission = creditCommission;
        this.accountRestrictions = accountRestrictions;
        this.depositInterests = depositInterests;
    }

    public Client createClient(String name, String surname, String passport, String address) throws IllegalArgumentException {
        Client.ClientBuilder builder = new Client.ClientBuilder();
        UUID id = UUID.randomUUID();
        Client client = builder.withId(id)
                .withName(name)
                .withBank(this)
                .withSurname(surname)
                .withPassport(passport)
                .withAddress(address)
                .build();
        clients.put(id, client);
        return client;
    }

    public CreditAccount createCreditAccount(UUID clientId, Client client) {
        UUID id = UUID.randomUUID();
        CreditAccount account = new CreditAccount(id, client);
        HashMap<UUID, Account> map = accounts.get(clientId);
        map.put(id, account);
        accounts.put(clientId, map);
        return account;
    }

    public DebitAccount createDebitAccount(UUID clientId, Client client) {
        UUID id = UUID.randomUUID();
        DebitAccount account = new DebitAccount(id, client);
        HashMap<UUID, Account> map = accounts.get(clientId);
        map.put(id, account);
        accounts.put(clientId, map);
        return account;
    }

    public DepositAccount createDepositAccount(UUID clientId, Client client, BigDecimal balance, LocalDate start, LocalDate end) {
        UUID id = UUID.randomUUID();
        float percent = depositInterests.stream().filter(pair -> pair.deposit().compareTo(balance) > 0).findFirst().get().interest();
        DepositAccount account = new DepositAccount(id, client, balance, start, end, percent);
        HashMap<UUID, Account> map = accounts.get(clientId);
        map.put(id, account);
        accounts.put(clientId, map);
        return account;
    }

    public Client findClient(UUID id) {
        return clients.get(id);
    }

    public void getNotify(LocalDate today) {
        for (HashMap<UUID, Account> bankAccounts : accounts.values()) {
            for (Account account : bankAccounts.values()) {
                if (today.getDayOfMonth() == 1)
                    account.newMonth();
                else
                    account.newDay();
            }
        }
    }

    public void setNewInterestOnBalance(float newInterestOnBalance) {
        this.interestOnBalance = newInterestOnBalance;
        this.notifySubscribers("New interest on balance");
    }

    public void setNewCreditCommission(BigDecimal newCreditCommission) {
        this.creditCommission = newCreditCommission;
        this.notifySubscribers("New credit commission");
    }

    public void setNewAccountRestriction(BigDecimal newAccountRestriction) {
        this.accountRestrictions = newAccountRestriction;
        this.notifySubscribers("New account restriction");
    }

    public void setNewDepositInterests(ArrayList<DepositAndInterest> newDepositInterests) {
        newDepositInterests.sort(DepositAndInterest.ByDepositAscending);
        this.depositInterests = newDepositInterests;
        this.notifySubscribers("New deposit interests");
    }
    public static class BankBuilder {
        private UUID id;
        private String name;
        private float interestOnBalance;
        private BigDecimal creditCommission;

        private BigDecimal accountRestrictions;
        private ArrayList<DepositAndInterest> depositInterests;

        public BankBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public BankBuilder withName(String name) throws IllegalArgumentException {
            if (name.isEmpty())
                throw new IllegalArgumentException("You must indicate the name of the bank");
            this.name = name;
            return this;
        }

        public BankBuilder withInterestOnBalance(float interestOnBalance) throws IllegalArgumentException {
            if (interestOnBalance < 0)
                throw new IllegalArgumentException("Interest on balance can't be less than 0");

            this.interestOnBalance = interestOnBalance;
            return this;
        }

        public BankBuilder withCreditCommission(BigDecimal creditCommission) throws IllegalArgumentException {
            if (creditCommission.compareTo(new BigDecimal(0)) < 0)
                throw new IllegalArgumentException("Credit commission can't be less than 0");

            this.creditCommission = creditCommission;
            return this;
        }

        public BankBuilder withAccountRestrictions(BigDecimal accountRestrictions) throws IllegalArgumentException {
            if (accountRestrictions.compareTo(new BigDecimal(0)) < 0)
                throw new IllegalArgumentException("Account restriction can't be less than 0");

            this.accountRestrictions = accountRestrictions;
            return this;
        }

        public BankBuilder withDepositInterests(ArrayList<DepositAndInterest> depositInterests) throws IllegalArgumentException {
            depositInterests.sort(DepositAndInterest.ByDepositAscending);
            Optional<DepositAndInterest> withNegativeBalance = depositInterests.stream().filter(pair -> pair.deposit().compareTo(new BigDecimal(0)) < 0).findFirst();

            if (withNegativeBalance.isEmpty())
                throw new IllegalArgumentException("Deposit can't be less 0");
            Optional<DepositAndInterest> withNegativeInterest = depositInterests.stream().filter(pair -> pair.interest() < 0).findFirst();

            if (withNegativeInterest.isEmpty())
                throw new IllegalArgumentException("Interest can't be less 0");

            this.depositInterests = depositInterests;
            return this;
        }

        public Bank build() {
            return new Bank(id, name, interestOnBalance, creditCommission, accountRestrictions, depositInterests);
        }
    }
}
