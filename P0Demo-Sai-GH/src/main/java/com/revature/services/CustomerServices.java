package com.revature.services;

import com.revature.DAO.CustomerDAO;
import com.revature.models.Customer;

import java.util.ArrayList;

public class CustomerServices {
    // Instantiate CustomerDAO for its methods
    CustomerDAO customerDAO = new CustomerDAO();

    // Get all customers method for services
    public ArrayList<Customer> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }

    // Get customer by cust_id
    public Customer getCustById(int cust_id) throws IllegalArgumentException{
        // Positive ID check
        if(cust_id <= 0){
            throw new IllegalArgumentException("Customer IDs must be positive");
        }

        return customerDAO.getCustomerById(cust_id);
    }

    // Add new Customer to DB
    public Customer addCustomer(Customer customer) throws IllegalArgumentException{

        ArrayList<Customer> custList = customerDAO.getAllCustomers();

        // Unique Email check
        for (Customer custObj : custList) {
            if (custObj.getCust_email().equals(customer.getCust_email())) {
                throw new IllegalArgumentException("Emails are unique, entered value is already taken");
            }
        }

        customerDAO.addCustomer(customer);

        return customer;
    }

    // Add new Customer to DB
    public String updateCustomerEmail(int cust_id, String newEmail) throws IllegalArgumentException{

        // Positive ID check
        if(cust_id <= 0){
            throw new IllegalArgumentException("Customer IDs must be positive");
        }

        ArrayList<Customer> custList = customerDAO.getAllCustomers();

        // Unique Email check
        for (Customer custObj : custList) {
            if (custObj.getCust_email().equals(newEmail)) {
                throw new IllegalArgumentException("Emails are unique, entered value is already taken");
            }
        }

        customerDAO.updateCustomerEmail(cust_id, newEmail);

        return newEmail;
    }
}
