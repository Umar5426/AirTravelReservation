package main.java.model;

import java.util.Date;

public class Customer {

    private int id;             // DB primary key
    private String customerID;  // business-level ID

    private String fname;
    private String lname;
    private Date dob;

    private String username;
    private String password;

    private String email;
    private String phone;

    // Full constructor for DB use
    public Customer(int id, String customerID, String fname, String lname,
                    Date dob, String username, String password,
                    String email, String phone) {

        this.id = id;
        this.customerID = customerID;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    // Minimal constructor if needed
    public Customer(String fname, String lname, Date dob,
                    String customerID, String username, String password) {

        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.customerID = customerID;
        this.username = username;
        this.password = password;
    }

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

    // Setters as needed
}

