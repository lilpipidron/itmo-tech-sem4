package ru.itmo.clients;

public record Client(String name, String surname, String password, String address) {

    public static class ClientBuilder {
        private String name = null;
        private String surname = null;
        private String password = null;
        private String address = null;

        public ClientBuilder SetName(String name) throws IllegalArgumentException {
            if (name.isEmpty()){
                throw new IllegalArgumentException("You must provide a name");
            }
            this.name = name;
            return this;
        }

        public ClientBuilder SetSurname(String surname) throws  IllegalArgumentException {
            if (surname.isEmpty()){
                throw new IllegalArgumentException("You must provide a surname");
            }
            this.surname = surname;
            return this;
        }

        public ClientBuilder SetPassword(String password){
            this.password = password;
            return this;
        }

        public ClientBuilder SetAddress(String address){
            this.address = address;
            return this;
        }

        public Client Build(){
            return new Client(name, surname, password, address);
        }
    }
}
