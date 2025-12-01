package main.java.model;

import java.util.Date;

public class Customer {
    public String fname;
    private String lname;
    private Date dob;
    private String customerID; // auto-generated

    private String username;
    private String password;

    // Static counter for generating unique IDs
    private static int nextId = 1;

    // Constructor without customerID
    public Customer(String fname, String lname, Date dob, String username, String password) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.username = username;
        this.password = password;

        assignUniqueID();
    }

    // -----------------------------
    // Constructor for EXISTING customers (from DB)
    // -----------------------------
    public Customer(String fname, String lname, Date dob, String customerID,
                    String username, String password) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.customerID = customerID; // use existing ID from DB
        this.username = username;
        this.password = password;
    }

    // Assigns a unique customerID automatically
    private void assignUniqueID() {
        this.customerID = "C" + nextId++;
    }

    // Optionally, reset the ID counter (useful for testing)
    public static void resetIDCounter() {
        nextId = 1;
    }

    // Getters
    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public Date getDob() { return dob; }
    public String getCustomerID() { return customerID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Password setter
    public void setPassword(String newPassword) { this.password = newPassword; }
}
