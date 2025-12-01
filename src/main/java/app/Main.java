package main.java.app;

import main.java.dao.DatabaseConnector;
import main.java.dao.DatabaseSetup;
import main.java.dao.DatabaseSeeder;
import main.java.gui.LoginWindow;

public class Main {
    public static void main(String[] args) {

        // 1. Create tables if they do not exist
        DatabaseSetup.createTables();

        // 2. Test DB connection (prints confirmation)
        DatabaseConnector.connect();

        // 3. Seed default data (customers, agents, flights)
        DatabaseSeeder.seed();

        // 4. Open the Login GUI
        new LoginWindow().setVisible(true);
    }
}
