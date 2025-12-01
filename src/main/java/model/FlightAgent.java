package main.java.model;

import java.util.Date;

public class FlightAgent extends Customer {
    private String authorizedKey;

    public FlightAgent(String fname, String lname, Date dob, String customerID, String username, String password, String authorizedKey) {
        super(fname, lname, dob, customerID, username, password);
        this.authorizedKey = authorizedKey;
    }

    public void addCustomer(){};

    public void viewCustomer(){};

    public void editCustomer(){};

    public void setAuthorizedKey(String authorizedKey) {
        this.authorizedKey = authorizedKey;

    }


}
