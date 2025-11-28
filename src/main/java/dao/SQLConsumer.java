package main.java.dao;

import java.sql.SQLException;

// Functional interface to allow lambdas that throw SQLExceptions
@FunctionalInterface
public interface SQLConsumer<T> {
    void accept(T t) throws SQLException;
}