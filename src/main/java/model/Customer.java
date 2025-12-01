package main.java.model;

import java.util.Date;

public abstract class Customer {

    protected int id;             // primary key from database
    protected String customerID;  // business ID like C123, etc.

    protected String fname;
    protected String lname;
    protected Date dob;

    protected String username;
    protected String password;

    protected String email;
    protected String phone;

    // Empty constructor for flexibility
    public Customer() {}

    // Getters
    public int getId() { return id; }
    public String getCustomerID() { return customerID; }
    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public Date getDob() { return dob; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setCustomerID(String customerID) { this.customerID = customerID; }
    public void setFname(String fname) { this.fname = fname; }
    public void setLname(String lname) { this.lname = lname; }
    public void setDob(Date dob) { this.dob = dob; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}

