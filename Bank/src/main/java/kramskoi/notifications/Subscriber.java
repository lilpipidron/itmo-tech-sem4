/**
 * This class represents a Subscriber that can receive notifications.
 */
package ru.kramskoi.notifications;

import java.util.ArrayList;

public abstract class Subscriber {
    private ArrayList<String> messages;

    /**
     * Adds a new notification message to the subscriber's list of messages.
     * @param message the notification message to add
     */
    public void newNotification(String message) {
        messages.add(message);
    }

    /**
     * Abstract method to subscribe a subscriber to notifications.
     */
    public abstract void subscribe();

    /**
     * Abstract method to unsubscribe a subscriber from notifications.
     */
    public abstract void unsubscribe();
}