/**
 * Represents a bank with various account types and client management capabilities.
 * This class extends {@link Publisher} to allow subscribers to be notified of changes.
 */
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

    /**
     * Constructs a new Bank with the specified parameters.
     *
     * @param id                 the unique identifier of the bank
     * @param name                the name of the bank
     * @param interestOnBalance   the interest rate applied on the balance
     * @param creditCommission    the commission rate for credit accounts
     * @param accountRestrictions the restrictions applied to accounts
     * @param depositInterests    the interest rates for deposit accounts
     */
    private Bank(UUID id, String name, float interestOnBalance, BigDecimal creditCommission, BigDecimal accountRestrictions, ArrayList<DepositAndInterest> depositInterests) {
        this.id = id;
        this.name = name;
        this.interestOnBalance = interestOnBalance;
        this.creditCommission = creditCommission;
        this.accountRestrictions = accountRestrictions;
        this.depositInterests = depositInterests;
    }

    /**
     * Creates a new client and adds them to the bank's client list.
     *
     * @param name       the client's name
     * @param surname    the client's surname
     * @param passport   the client's passport number
     * @param address    the client's address
     * @return the created client
     * @throws IllegalArgumentException if any of the provided arguments are invalid
     */
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

    /**
     * Creates a new credit account for a client and adds it to the bank's account list.
     *
     * @param clientId the unique identifier of the client
     * @param client   the client for whom the account is being created
     * @return the created credit account
     */
    public CreditAccount createCreditAccount(UUID clientId, Client client) {
        UUID id = UUID.randomUUID();
        CreditAccount account = new CreditAccount(id, client);
        HashMap<UUID, Account> map = accounts.get(clientId);
        map.put(id, account);
        accounts.put(clientId, map);
        return account;
    }

    /**
     * Creates a new debit account for a client and adds it to the bank's account list.
     *
     * @param clientId the unique identifier of the client
     * @param client   the client for whom the account is being created
     * @return the created debit account
     */
    public DebitAccount createDebitAccount(UUID clientId, Client client) {
        UUID id = UUID.randomUUID();
        DebitAccount account = new DebitAccount(id, client);
        HashMap<UUID, Account> map = accounts.get(clientId);
        map.put(id, account);
        accounts.put(clientId, map);
        return account;
    }

    /**
     * Creates a new deposit account for a client with the specified balance, start, and end dates, and adds it to the bank's account list.
     *
     * @param clientId the unique identifier of the client
     * @param client   the client for whom the account is being created
     * @param balance the initial balance of the deposit account
     * @param start    the start date of the deposit
     * @param end      the end date of the deposit
     * @return the created deposit account
     */
    public DepositAccount createDepositAccount(UUID clientId, Client client, BigDecimal balance, LocalDate start, LocalDate end) {
        UUID id = UUID.randomUUID();
        float percent = depositInterests.stream().filter(pair -> pair.deposit().compareTo(balance) > 0).findFirst().get().interest();
        DepositAccount account = new DepositAccount(id, client, balance, start, end, percent);
        HashMap<UUID, Account> map = accounts.get(clientId);
        map.put(id, account);
        accounts.put(clientId, map);
        return account;
    }

    /**
     * Finds a client by their unique identifier.
     *
     * @param id the unique identifier of the client
     * @return the client if found, otherwise null
     */
    public Client findClient(UUID id) {
        return clients.get(id);
    }

    public Account findAccount(UUID clientId, UUID accountId){
        return accounts.get(clientId).get(accountId);
    }

    /**
     * Notifies all accounts of the bank about the start of a new day or month.
     *
     * @param today the current date
     */
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

    /**
     * Sets a new interest rate on balance and notifies subscribers.
     *
     * @param newInterestOnBalance the new interest rate on balance
     */
    public void setNewInterestOnBalance(float newInterestOnBalance) {
        this.interestOnBalance = newInterestOnBalance;
        this.notifySubscribers("New interest on balance");
    }

    /**
     * Sets a new credit commission rate and notifies subscribers.
     *
     * @param newCreditCommission the new credit commission rate
     */
    public void setNewCreditCommission(BigDecimal newCreditCommission) {
        this.creditCommission = newCreditCommission;
        this.notifySubscribers("New credit commission");
    }

    /**
     * Sets new account restrictions and notifies subscribers.
     *
     * @param newAccountRestriction the new account restrictions
     */
    public void setNewAccountRestriction(BigDecimal newAccountRestriction) {
        this.accountRestrictions = newAccountRestriction;
        this.notifySubscribers("New account restriction");
    }

    /**
     * Sets new deposit interests and notifies subscribers.
     *
     * @param newDepositInterests the new deposit interests
     */
    public void setNewDepositInterests(ArrayList<DepositAndInterest> newDepositInterests) {
        newDepositInterests.sort(DepositAndInterest.ByDepositAscending);
        this.depositInterests = newDepositInterests;
        this.notifySubscribers("New deposit interests");
    }

    /**
     * Builder class for creating a new Bank instance.
     * This class allows for a fluent interface to set various properties of the Bank.
     */
    public static class BankBuilder {
        private UUID id;
        private String name;
        private float interestOnBalance;
        private BigDecimal creditCommission;
        private BigDecimal accountRestrictions;
        private ArrayList<DepositAndInterest> depositInterests;

        /**
         * Sets the unique identifier of the bank.
         *
         * @param id the unique identifier
         * @return the builder instance
         */
        public BankBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the bank.
         *
         * @param name the name of the bank
         * @return the builder instance
         * @throws IllegalArgumentException if the name is empty
         */
        public BankBuilder withName(String name) throws IllegalArgumentException {
            if (name.isEmpty())
                throw new IllegalArgumentException("You must indicate the name of the bank");
            this.name = name;
            return this;
        }

        /**
         * Sets the interest rate applied on the balance.
         *
         * @param interestOnBalance the interest rate
         * @return the builder instance
         * @throws IllegalArgumentException if the interest rate is less than 0
         */
        public BankBuilder withInterestOnBalance(float interestOnBalance) throws IllegalArgumentException {
            if (interestOnBalance < 0)
                throw new IllegalArgumentException("Interest on balance can't be less than 0");

            this.interestOnBalance = interestOnBalance;
            return this;
        }

        /**
         * Sets the commission rate for credit accounts.
         *
         * @param creditCommission the commission rate
         * @return the builder instance
         * @throws IllegalArgumentException if the commission rate is less than 0
         */
        public BankBuilder withCreditCommission(                BigDecimal creditCommission) throws IllegalArgumentException {
            if (creditCommission.compareTo(new BigDecimal(0)) < 0)
                throw new IllegalArgumentException("Credit commission can't be less than 0");

            this.creditCommission = creditCommission;
            return this;
        }

        /**
         * Sets the restrictions applied to accounts.
         *
         * @param accountRestrictions the account restrictions
         * @return the builder instance
         * @throws IllegalArgumentException if the account restrictions are less than 0
         */
        public BankBuilder withAccountRestrictions(BigDecimal accountRestrictions) throws IllegalArgumentException {
            if (accountRestrictions.compareTo(new BigDecimal(0)) < 0)
                throw new IllegalArgumentException("Account restriction can't be less than 0");

            this.accountRestrictions = accountRestrictions;
            return this;
        }

        /**
         * Sets the interest rates for deposit accounts.
         *
         * @param depositInterests the deposit interests
         * @return the builder instance
         * @throws IllegalArgumentException if any deposit interest is less than 0 or if the deposit interests list is empty
         */
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

        /**
         * Builds a new Bank instance with the specified properties.
         *
         * @return the new Bank instance
         */
        public Bank build() {
            return new Bank(id, name, interestOnBalance, creditCommission, accountRestrictions, depositInterests);
        }
    }
}

