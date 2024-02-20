package ru.itmo.clients;

import java.util.UUID;

public class Client {

    private final UUID id;
    private final String name;
    private final String surname;
    private String passport = null;
    private String address = null;

    private Status status = Status.Dubious;

    public Client(UUID id, String name, String surname, String passport, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.passport = passport;
        this.address = address;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = Status.Normal;
    }

    public void setPassport(String passport) {
        this.passport = passport;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = Status.Normal;
    }

    public void setAddress(String address) {
        this.address = address;
        if (!passport.isEmpty() && !address.isEmpty())
            this.status = Status.Normal;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPassport() {
        return this.passport;
    }

    public Status getStatus() {
        return this.status;
    }

    public static class ClientBuilder {

        private UUID id;
        private String name = null;
        private String surname = null;
        private String passport = null;
        private String address = null;

        public ClientBuilder withId(UUID id) {
            this.id = id;
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
            return new Client(name, surname, passport, address);
        }
    }
}
