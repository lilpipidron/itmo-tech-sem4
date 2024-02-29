package ru.itmo.clients;

import lombok.Getter;
import ru.itmo.banks.Bank;

import java.util.UUID;

public class Client {

    @Getter
    private final UUID id;
    @Getter
    private final Bank bank;
    private final String name;
    private final String surname;
    private String passport = null;
    private String address = null;
    @Getter
    private ClientStatus status = ClientStatus.Dubious;

    private Client(UUID id, Bank bank, String name, String surname, String passport, String address) {
        this.id = id;
        this.bank = bank;
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.address = address;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = ClientStatus.Normal;
    }

    public void setPassport(String passport) {
        this.passport = passport;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = ClientStatus.Normal;
    }

    public void setAddress(String address) {
        this.address = address;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = ClientStatus.Normal;
    }

    public static class ClientBuilder {

        private UUID id;

        private Bank bank;
        private String name = null;
        private String surname = null;
        private String passport = null;
        private String address = null;

        public ClientBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public ClientBuilder withBank(Bank bank) {
            this.bank = bank;
            return this;
        }

        public ClientBuilder withName(String name) throws IllegalArgumentException {
            if (name.isEmpty()) {
                throw new IllegalArgumentException("You must provide a name");
            }
            this.name = name;
            return this;
        }

        public ClientBuilder withSurname(String surname) throws IllegalArgumentException {
            if (surname.isEmpty()) {
                throw new IllegalArgumentException("You must provide a surname");
            }
            this.surname = surname;
            return this;
        }

        public ClientBuilder withPassport(String passport) {
            this.passport = passport;
            return this;
        }

        public ClientBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Client build() {
            return new Client(id, bank, name, surname, passport, address);
        }
    }
}
