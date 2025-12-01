package main.java.service;

import main.java.dao.CustomerDAO;
import main.java.model.Customer;

import java.util.Date;

public class CustomerService {

    private CustomerDAO customerDAO;

    private Customer loggedInCustomer;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    // ---------------------
    // Authentication
    // ---------------------

    /**
     * Attempt to login a customer using username and password.
     * If successful, sets loggedInCustomer and returns the object.
     * Returns null if login fails.
     */
    public Customer login(String username, String password) {
        Customer customer = customerDAO.login(username, password);
        if (customer != null) {
            loggedInCustomer = customer;
            return customer;
        }
        return null;
    }

    public void logout() {
        loggedInCustomer = null;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    // ---------------------
    // Customer CRUD
    // ---------------------

    public boolean addCustomer(String fname, String lname, Date dob, String username, String password) {
        return customerDAO.addCustomer(fname, lname, dob, username, password);
    }

    public boolean editCustomer(String customerID, String fname, String lname, Date dob, String password) {
        return customerDAO.editCustomer(customerID, fname, lname, dob, password);
    }

    public Customer viewCustomer(String username) {
        return customerDAO.viewCustomer(username);
    }

    public Customer fetchCustomerByID(String customerID) {
        return customerDAO.fetchById(customerID);
    }
}
