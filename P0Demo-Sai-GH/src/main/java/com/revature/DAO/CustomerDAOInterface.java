package com.revature.DAO;

import com.revature.models.Customer;

import java.util.ArrayList;

public interface CustomerDAOInterface {

    ArrayList<Customer> getAllCustomers();

    Customer getCustomerById(int cust_id);

    Customer addCustomer(Customer customer);

    String updateCustomerEmail(int cust_id, String newEmail);
}
