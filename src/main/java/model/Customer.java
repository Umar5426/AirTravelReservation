package main.java.model;

import java.util.Date;

public class Customer {
    private String fname;
    private String lname;
    private Date dob;
    private String customerID;

    private String username;
    private String password;

    public Customer(String fname, String lname, Date dob, String customerID,
                    String username, String password) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.customerID = customerID;
        this.username = username;
        this.password = password;
    }

    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public Date getDob() { return dob; }
    public String getCustomerID() { return customerID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setPassword(String newPassword) { this.password = newPassword; }
}
