package com.revature.controllers;

import com.revature.models.Rental;
import com.revature.services.RentalServices;

import java.util.ArrayList;
import io.javalin.http.Handler;

public class RentalController {

    // Rental service object
    RentalServices rentalServices = new RentalServices();

    // Handler for GET requests for "/rental-records
    public Handler getAllRentalsHandler = ctx -> {
        // ArrayList of rentals, populated by getAllRentals service method
        ArrayList<Rental> rentals = rentalServices.getAllRentals();

        // Sending HTTP Response in JSON to endpoint
        ctx.json(rentals);

        // HTTP Response status code -> OK
        ctx.status(200);
    };

    // Handler for GET requests for "/cust-rentals/{id}
    public Handler getRentalsByCustIdHandler = ctx -> {
        // Extract path parameter {id} from HTTP Request
        int cust_id = Integer.parseInt(ctx.pathParam("id"));

        // ArrayList of rentals, populated by getAllRentals service method
        ArrayList<Rental> rentals = rentalServices.getRentalsByCustId(cust_id);

        // Sending HTTP Response in JSON to endpoint
        ctx.json(rentals);

        // HTTP Response status code -> OK
        ctx.status(200);
    };

    // Handler for GET requests for "/movie-rentals/{id}
    public Handler getRentalsByMovieIdHandler = ctx -> {
        // Extract path parameter {id} from HTTP Request
        int movie_id = Integer.parseInt(ctx.pathParam("id"));

        // ArrayList of rentals, populated by getAllRentals service method
        ArrayList<Rental> rentals = rentalServices.getRentalsByMovieId(movie_id);

        // Sending HTTP Response in JSON to endpoint
        ctx.json(rentals);

        // HTTP Response status code -> OK
        ctx.status(200);
    };

    // Handler for POST requests to "/add-rental"
    public Handler addNewRentalHandler = ctx -> {
        // Format incoming JSON data into Class object for DAO class using ctx.bodyAsClass()
        Rental newRental = ctx.bodyAsClass(Rental.class);

        try {
            //Send new object to the service to be inserted into the DB
            Rental addedRental = rentalServices.addRental(newRental);
            ctx.status(201); // Resource created -> 201
            ctx.json(addedRental); // Send added Object back to user as JSON
        } catch (IllegalArgumentException e){
            ctx.status(400); // Bad Request -> 400
            ctx.result(e.getMessage()); // Send back specific error message
        }
    };

    // Handler for DELETE requests to "/remove-rental/{id}"
    public Handler removeRentalsHandler = ctx -> {
        // Extract path parameter {id} from HTTP Request
        int rental_id = Integer.parseInt(ctx.pathParam("id"));

        try {
            //Send rental id to the service to be referenced to remove record from the DB
            rentalServices.removeRental(rental_id);
            ctx.status(202); // Remove request accedpted -> 202
            ctx.result("Rental record with ID: " + rental_id + " has been removed.");
        } catch (IllegalArgumentException e){
            ctx.status(404); // Record to remove was Not Found -> 404
            ctx.result(e.getMessage()); // Send back specific error message
        }
    };



}
