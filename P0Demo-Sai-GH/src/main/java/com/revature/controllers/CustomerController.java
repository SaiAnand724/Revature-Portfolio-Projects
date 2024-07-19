package com.revature.controllers;

import com.revature.models.Customer;
import com.revature.services.CustomerServices;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class CustomerController {


    // Customer service object
    CustomerServices customerServices = new CustomerServices();

    // Handler for GET requests for "/customers-list"
    public Handler getAllCustomersHandler = ctx -> {
        // ArrayList of rentals, populated by getAllCustomers service method
        ArrayList<Customer> customers = customerServices.getAllCustomers();

        // Sending HTTP Response in JSON to endpoint
        ctx.json(customers);

        // HTTP Response status code -> OK
        ctx.status(200);
    };

    // Handler for GET requests for "/customer/{id}"
    public Handler getCustomerByIdHandler = ctx -> {
        // Extract path parameter {id} from HTTP Request
        int cust_id = Integer.parseInt(ctx.pathParam("id"));

        // Instantiate a Customer object using DAO method and cust_id from above
        Customer customer = customerServices.getCustById(cust_id);

        if(customer == null){
            ctx.status(400); // Bad Request -> 400
            ctx.result("Customer not found!");
        } else {
            ctx.status(200); // OK -> 200
            ctx.json(customer); //send the Customer back as JSON
        }

    };

    // Handler for POST requests to "/add-customer"
    public Handler addNewCustHandler = ctx -> {
        // Format incoming JSON data into Class object for DAO class using ctx.bodyAsClass()
        Customer newCust = ctx.bodyAsClass(Customer.class);

        try {
            //Send new object to the service to be inserted into the DB
            Customer addedCust = customerServices.addCustomer(newCust);
            ctx.status(201); // Resource created -> 201
            ctx.json(addedCust); // Send added Object back to user as JSON
        } catch (IllegalArgumentException e){
            ctx.status(400); // Bad Request -> 400
            ctx.result(e.getMessage()); // Send back specific error message
        }
    };

    // Handler for PATCH requests to "/update-custEmail/{id}"
    public Handler updateCustomerEmail = ctx -> {
        int cust_id = Integer.parseInt(ctx.pathParam("id"));
        String newEmail = ctx.body();

        try {
            //send the two values above to the service
            customerServices.updateCustomerEmail(cust_id, newEmail);
            ctx.status(202); // Accepted request -> 202
            ctx.result("Customer ID: " + cust_id + " email has been updated to: " + newEmail);
        } catch (IllegalArgumentException e) {
            ctx.status(400); // Bad request - 400
            ctx.result(e.getMessage());
        }
    };
}
