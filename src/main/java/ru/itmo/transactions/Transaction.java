package ru.itmo.transactions;

import ru.itmo.exceptions.TransactionException;

public interface Transaction {
    void execute() throws TransactionException;

    void cancel() throws TransactionException;
}
