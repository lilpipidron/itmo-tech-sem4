package ru.itmo.notifications;

import java.util.ArrayList;

public abstract class Publisher {
    private ArrayList<Subscriber> subscribers;

    public void addSubscriber(Subscriber account) {
        subscribers.add(account);
    }
    public void deleteSubscriber(Subscriber account) {
        subscribers.remove(account);
    }

    public void notifySubscribers(String message) {
        subscribers.forEach(account -> account.newNotification(message));
    }
}
