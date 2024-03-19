/**
 * Represents a client of a bank with associated information.
 */
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
    private ClientStatus status = ClientStatus.DUBIOUS;

    /**
     * Constructs a new client with the provided details.
     *
     * @param id       The unique identifier of the client.
     * @param bank     The bank the client is associated with.
     * @param name     The first name of the client.
     * @param surname  The last name of the client.
     * @param passport The passport number of the client.
     * @param address  The address of the client.
     */
    private Client(UUID id, Bank bank, String name, String surname, String passport, String address) {
        this.id = id;
        this.bank = bank;
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.address = address;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = ClientStatus.NORMAL;
    }

    /**
     * Sets the passport number of the client and updates the status if necessary.
     *
     * @param passport The new passport number to set.
     */
    public void setPassport(String passport) {
        this.passport = passport;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = ClientStatus.NORMAL;
    }

    /**
     * Sets the address of the client and updates the status if necessary.
     *
     * @param address The new address to set.
     */
    public void setAddress(String address) {
        this.address = address;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = ClientStatus.NORMAL;
    }

    /**
     * Builder class for creating instances of Client with optional parameters.
     */
    public static class ClientBuilder {

        private UUID id;

        private Bank bank;
        private String name = null;
        private String surname = null;
        private String passport = null;
        private String address = null;

        /**
         * Sets the unique identifier for the client being built.
         *
         * @param id The unique identifier to set.
         * @return A reference to this builder to support method chaining.
         */
        public ClientBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the bank associated with the client being built.
         *
         * @param bank The bank to associate with the client.
         * @return A reference to this builder to support method chaining.
         */
        public ClientBuilder withBank(Bank bank) {
            this.bank = bank;
            return this;
        }

        /**
         * Sets the first name of the client being built.
         *
         * @param name The first name to set.
         * @return A reference to this builder to support method chaining.
         */
        public ClientBuilder withName(String name) {
            if (name.isEmpty()) {
                throw new IllegalArgumentException("You must provide a name");
            }
            this.name = name;
            return this;
        }

        /**
         * Sets the last name of the client being built.
         *
         * @param surname The last name to set.
         * @return A reference to this builder to support method chaining.
         */
        public ClientBuilder withSurname(String surname) {
            if (surname.isEmpty()) {
                throw new IllegalArgumentException("You must provide a surname");
            }
            this.surname = surname;
            return this;
        }

        /**
         * Sets the passport number for the client being built.
         *
         * @param passport The passport number to set.
         * @return A reference to this builder to support method chaining.
         */
        public ClientBuilder withPassport(String passport) {
            this.passport = passport;
            return this;
        }

        /**
         * Sets the address for the client being built.
         *
         * @param address The address to set.
         * @return A reference to this builder to support method chaining.
         */
        public ClientBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Builds and returns a new instance of Client based on the provided parameters.
         *
         * @return A new instance of Client with the specified details.
         */
        public Client build() {
            return new Client(id, bank, name, surname, passport, address);
        }
    }
}