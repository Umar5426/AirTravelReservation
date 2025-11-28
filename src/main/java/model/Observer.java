package main.java.model;

public interface Observer<E> {
    void update(E event);
}
