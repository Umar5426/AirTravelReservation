package main.java.app;

import main.java.dao.DatabaseInitializer;
import main.java.gui.LoginWindow;

public class Main {
    public static void main(String[] args) {
        // Ensure DB connection is available before showing UI
        DatabaseInitializer.initializeDatabase();
        new LoginWindow().setVisible(true);
    }
}
