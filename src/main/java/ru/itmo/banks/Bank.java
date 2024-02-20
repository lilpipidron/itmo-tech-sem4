package ru.itmo.banks;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class Bank {

    private UUID id;
    private String name;
    private float interestOnBalance;
    private float creditCommission;
    private ArrayList<DepositAndInterest> depositInterests;

    private Bank(UUID id, String name, float interestOnBalance, float creditCommission, ArrayList<DepositAndInterest> depositInterests) {
        this.id = id;
        this.name = name;
        this.interestOnBalance = interestOnBalance;
        this.creditCommission = creditCommission;
        this.depositInterests = depositInterests;
    }

    public static class BankBuilder {
        private UUID id;
        private String name;
        private float interestOnBalance;
        private float creditCommission;
        private ArrayList<DepositAndInterest> depositInterests;

        public BankBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public BankBuilder setName(String name) throws IllegalArgumentException {
            if (name.isEmpty())
                throw new IllegalArgumentException("You must indicate the name of the bank");
            this.name = name;
            return this;
        }

        public BankBuilder setInterestOnBalance(float interestOnBalance) throws IllegalArgumentException {
            if (interestOnBalance < 0)
                throw new IllegalArgumentException("Interest on balance can't be less than 0");

            this.interestOnBalance = interestOnBalance;
            return this;
        }

        public BankBuilder setCreditCommission(float creditCommission) throws IllegalArgumentException {
            if (creditCommission < 0)
                throw new IllegalArgumentException("Credit commission can't be less than 0");

            this.creditCommission = creditCommission;
            return this;
        }

        public BankBuilder setDepositInterests(ArrayList<DepositAndInterest> depositInterests) throws IllegalArgumentException {
            depositInterests.sort(DepositAndInterest.ByDepositAscending);
            Optional<DepositAndInterest> withNegativeBalance = depositInterests.stream().filter(pair -> pair.deposit() < 0).findFirst();

            if (withNegativeBalance.isEmpty())
                throw new IllegalArgumentException("Deposit can't be less 0");
            Optional<DepositAndInterest> withNegativeInterest = depositInterests.stream().filter(pair -> pair.interest() < 0).findFirst();

            if (withNegativeInterest.isEmpty())
                throw new IllegalArgumentException("Interest can't be less 0");

            this.depositInterests = depositInterests;
            return this;
        }

        public Bank build() {
            return new Bank(id, name, interestOnBalance, creditCommission, depositInterests);
        }
    }
}
