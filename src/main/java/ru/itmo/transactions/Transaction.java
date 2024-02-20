package ru.itmo.transactions;

public interface Transaction {
    void execute();

    void cancel();
}
