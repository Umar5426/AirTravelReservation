package main.java.model;

/*
 * Subject interface
 * The Subject manages a list of observers and notifies them when an event occurs.
 */
public interface Subject {
    void register(Observer observer);
    void unregister(Observer observer);
}
