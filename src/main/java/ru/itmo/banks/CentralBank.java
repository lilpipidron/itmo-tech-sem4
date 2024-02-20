package ru.itmo.banks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CentralBank {
    private HashMap<UUID, Bank> banks;

    public CentralBank(){
        banks = new HashMap<>();
    }

    public Bank createBank(String name, float interestOnBalance, float creditCommission, ArrayList<DepositAndInterest>
            depositInterests) throws IllegalArgumentException {
        Bank.BankBuilder bankBuilder = new Bank.BankBuilder();
        UUID id = UUID.randomUUID();
        Bank bank = bankBuilder.setId(id)
                .setInterestOnBalance(interestOnBalance)
                .setCreditCommission(creditCommission)
                .setDepositInterests(depositInterests)
                .build();

        banks.put(id, bank);
        return bank;
    }

    public HashMap<UUID, Bank> getBanks(){
        return banks;
    }
}
