package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DatabaseSeeder {

    public static void seed() {
        try (Connection conn = DatabaseConnector.connect()) {

            // ---- Insert Customers ----
            PreparedStatement c1 = conn.prepareStatement(
                "INSERT OR IGNORE INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?)");
            c1.setString(1, "C1");
            c1.setString(2, "Umar");
            c1.setString(3, "Khan");
            c1.setString(4, "2003-10-14");
            c1.setString(5, "umarkhan");
            c1.setString(6, "password123");
            c1.setString(7, "customer");
            c1.executeUpdate();

            PreparedStatement c2 = conn.prepareStatement(
                "INSERT OR IGNORE INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?)");
            c2.setString(1, "A1");
            c2.setString(2, "Joe");
            c2.setString(3, "Smith");
            c2.setString(4, "1999-01-01");
            c2.setString(5, "AgentSmith");
            c2.setString(6, "password123");
            c2.setString(7, "agent");
            c2.executeUpdate();

            // ---- Insert Flights ----
            PreparedStatement f1 = conn.prepareStatement(
                "INSERT OR IGNORE INTO Flight VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            f1.setString(1, "F1");
            f1.setString(2, "AC6778");
            f1.setString(3, "AirCanada");
            f1.setString(4, "2025-12-25");
            f1.setString(5, "4hr");
            f1.setString(6, "YYC");
            f1.setString(7, "YYZ");
            f1.setDouble(8, 400);
            f1.executeUpdate();

            PreparedStatement f2 = conn.prepareStatement(
                "INSERT OR IGNORE INTO Flight VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            f2.setString(1, "F2");
            f2.setString(2, "WJ408");
            f2.setString(3, "WestJet");
            f2.setString(4, "2025-12-26");
            f2.setString(5, "4hr");
            f2.setString(6, "YYZ");
            f2.setString(7, "YYC");
            f2.setDouble(8, 400);
            f2.executeUpdate();

            System.out.println("Database seeded.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
