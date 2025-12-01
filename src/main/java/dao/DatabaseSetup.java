package main.java.dao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {
    public static void createTables() {
        String customerTable = "CREATE TABLE IF NOT EXISTS Customer ("
                + "customer_id TEXT PRIMARY KEY,"
                + "fname TEXT NOT NULL,"
                + "lname TEXT NOT NULL,"
                + "dob DATE NOT NULL,"
                + "username TEXT UNIQUE NOT NULL,"
                + "password TEXT NOT NULL,"
                + "role TEXT NOT NULL DEFAULT 'customer' CHECK(role IN ('customer','agent'))"
                + ");";

        String flightTable = "CREATE TABLE IF NOT EXISTS Flight ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "flight_id TEXT UNIQUE,"
                + "flight_code TEXT NOT NULL,"
                + "airline TEXT NOT NULL,"
                + "flight_date DATE NOT NULL,"
                + "flight_duration TEXT NOT NULL,"
                + "departure_area_code TEXT NOT NULL,"
                + "arrival_area_code TEXT NOT NULL,"
                + "capacity INTEGER NOT NULL,"
                + "price REAL NOT NULL"
                + ");";

        String reservationTable = "CREATE TABLE IF NOT EXISTS Reservation ("
                + "reservation_id TEXT PRIMARY KEY,"
                + "booking_db_id INTEGER,"
                + "customer_id TEXT NOT NULL,"
                + "flight_id TEXT NOT NULL,"
                + "date_booked DATE NOT NULL,"
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
