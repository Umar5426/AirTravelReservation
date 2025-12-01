package main.java.model;

import java.util.Date;
import main.java.dao.CustomerDAO;

public class FlightAgent extends Customer {

    private String authorizedKey;
    private CustomerDAO customerDAO;  // DAO instance

    public FlightAgent(String fname, String lname, Date dob, String username, String password, String authorizedKey) {
        // Pass null for customerID; CustomerDAO handles unique ID assignment
        super(fname, lname, dob, null, username, password);
        this.authorizedKey = authorizedKey;
        this.customerDAO = new CustomerDAO();
    }

    // -----------------------------
    // Add a new customer
    // -----------------------------
    public boolean addCustomer(String fname, String lname, Date dob, String username, String password) {
        // CustomerDAO will handle ID assignment
        return customerDAO.addCustomer(fname, lname, dob, username, password);
    }

    // -----------------------------
    // View a customer by ID
    // -----------------------------
    public Customer viewCustomer(String customerID) {
        return customerDAO.fetchById(customerID);
    }

    // -----------------------------
    // Edit a customer
    // -----------------------------
    public boolean editCustomer(String customerID, String fname, String lname, Date dob, String password) {
        return customerDAO.editCustomer(customerID, fname, lname, dob, password);
    }

    // -----------------------------
    // Setter for authorizedKey
    // -----------------------------
    public void setAuthorizedKey(String authorizedKey) {
        this.authorizedKey = authorizedKey;
    }

    public String getAuthorizedKey() {
        return this.authorizedKey;
    }
}
