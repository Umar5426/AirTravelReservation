package main.java.dao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void createTables() {
        String customerTable = "CREATE TABLE IF NOT EXISTS Customer ("
                + "customer_id TEXT PRIMARY KEY,"
                + "fname TEXT NOT NULL,"
                + "lname TEXT NOT NULL,"
                + "dob TEXT NOT NULL,"  // store as ISO-8601 string
                + "username TEXT UNIQUE NOT NULL,"
                + "password TEXT NOT NULL,"
                + "role TEXT NOT NULL DEFAULT 'customer' "
                + "CHECK(role IN ('customer','agent'))"
                + ");";

        String flightTable = "CREATE TABLE IF NOT EXISTS Flight ("
                + "flight_id TEXT PRIMARY KEY,"
                + "flight_code TEXT NOT NULL,"
                + "airline TEXT NOT NULL,"
                + "flight_date TEXT NOT NULL,"
                + "flight_duration TEXT NOT NULL,"
                + "departure_area_code TEXT NOT NULL,"
                + "arrival_area_code TEXT NOT NULL,"
                + "price REAL NOT NULL"
                + ");";

        String reservationTable = "CREATE TABLE IF NOT EXISTS Reservation ("
                + "reservation_id TEXT PRIMARY KEY,"
                + "customer_id TEXT NOT NULL,"
                + "flight_id TEXT NOT NULL,"
                + "date_booked TEXT NOT NULL,"
                + "total_price REAL NOT NULL,"
                + "FOREIGN KEY(customer_id) REFERENCES Customer(customer_id),"
                + "FOREIGN KEY(flight_id) REFERENCES Flight(flight_id)"
                + ");";


        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(customerTable);
            stmt.execute(flightTable);
            stmt.execute(reservationTable);
            System.out.println("Tables created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
