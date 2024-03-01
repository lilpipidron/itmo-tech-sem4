package ru.itmo.notifications;

import java.util.ArrayList;

public abstract class Subscriber {
    private ArrayList<String> messages;

    public void newNotification(String message) {
        messages.add(message);
    }

    public abstract void subscribe();

    public abstract void unsubscribe();
}
