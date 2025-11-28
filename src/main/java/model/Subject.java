package main.java.model;

public interface Subject<E> {
    void register(Observer<E> observer);
    void unregister(Observer<E> observer);
}
