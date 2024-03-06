/**
 * This interface represents a transaction in the system.
 */
package ru.itmo.transactions;

import ru.itmo.exceptions.TransactionException;

public interface Transaction {
    /**
     * Executes the transaction.
     *
     * @throws TransactionException if an error occurs during the execution of the transaction.
     */
    void execute() throws TransactionException;

    /**
     * Cancels the transaction.
     *
     * @throws TransactionException if an error occurs during the cancellation of the transaction.
     */
    void cancel() throws TransactionException;
}