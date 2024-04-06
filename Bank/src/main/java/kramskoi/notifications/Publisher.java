/**
 * Abstract class for a publisher that manages a list of subscribers and notifies them when a new message is received.
 */
package ru.kramskoi.notifications;

import java.util.ArrayList;

public class Publisher {
    private ArrayList<Subscriber> subscribers;

    /**
     * Constructs a new Publisher object with an empty list of subscribers.
     */
    public Publisher() {
        subscribers = new ArrayList<>();
    }

    /**
     * Adds a subscriber to the list of subscribers.
     *
     * @param account the subscriber to add
     */
    public void addSubscriber(Subscriber account) {
        subscribers.add(account);
    }

    /**
     * Removes a subscriber from the list of subscribers.
     *
     * @param account the subscriber to remove
     */
    public void deleteSubscriber(Subscriber account) {
        subscribers.remove(account);
    }

    /**
     * Notifies all subscribers about a new message.
     *
     * @param message the message to notify subscribers about
     */
    public void notifySubscribers(String message) {
        subscribers.forEach(account -> account.newNotification(message));
    }
}