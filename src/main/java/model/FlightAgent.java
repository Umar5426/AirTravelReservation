package main.java.model;

import java.util.Date;
import main.java.dao.CustomerDAO;

public class FlightAgent extends Customer {
    private String authorizedKey;

    public FlightAgent(String fname, String lname, Date dob, String customerID, String username, String password, String authorizedKey) {
        super(fname, lname, dob, customerID, username, password);
        this.authorizedKey = authorizedKey;
    }

    public void addCustomer(String fname, String lname, Date dob, String customerID,
                    String username, String password){

        CustomerDAO addCustomer(fname, lname, dob, customerID, username, password);


    };

    public Customer viewCustomer(String customerID){
        Customer c.findbyId(customerID);
    };

    public void editCustomer(){};

    public void setAuthorizedKey(String authorizedKey) {
        this.authorizedKey = authorizedKey;

    }


}
