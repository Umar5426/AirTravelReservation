package main.java.model;

import java.util.Date;

public class Customer {
    private int id;
    private String fname;
    private String lname;
    private Date dob;
    private String customerID;

    private String username;
    private String password;
    private String email;
    private String phone;

    public Customer(int id,
                    String fname,
                    String lname,
                    Date dob,
                    String customerID,
                    String username,
                    String password,
                    String email,
                    String phone) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.customerID = customerID;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Legacy constructor for tests/older callers (no DB id, email, phone).
     */
    public Customer(String fname, String lname, Date dob, String customerID,
                    String username, String password) {
        this(-1, fname, lname, dob, customerID, username, password, null, null);
    }

    public int getId() { return id; }
    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public Date getDob() { return dob; }
    public String getCustomerID() { return customerID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void setPassword(String newPassword) { this.password = newPassword; }
}
