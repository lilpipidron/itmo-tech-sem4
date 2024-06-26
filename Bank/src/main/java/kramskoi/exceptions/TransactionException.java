/**
 * The TransactionException class represents an exception that can be thrown during transaction processing.
 */
package ru.kramskoi.exceptions;

public class TransactionException extends Exception {

    /**
     * Constructs a new TransactionException with the specified error message.
     *
     * @param errorMessage a String representing the error message associated with the exception
     */
    public TransactionException(String errorMessage) {
        super(errorMessage);
    }
}