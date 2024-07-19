package com.revature;

import com.revature.controllers.CustomerController;
import com.revature.controllers.MovieController;
import com.revature.controllers.RentalController;
import com.revature.models.Rental;
import com.revature.utils.ConnectionUtil;
import io.javalin.Javalin;

import java.sql.Connection;
import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) {

        // Postgres Database Connection setup -
        try(Connection conn = ConnectionUtil.getConnection()){
            System.out.println("POSTGRES DB CONNECTION SUCCESSFUL");
        } catch(SQLException e){
            // Handles SQLException if the connection can't be made
            // This also tells the issues through the stack trace
            e.printStackTrace();
            System.out.println("Unable to make Postgres Connection");
        }

        // Setup for Javalin app
        var app = Javalin.create(/*any extra configs would go here*/).start(3000)

        // Hello call for postman testing
        .get("/", ctx -> ctx.result("Hello Postman!"));

        // Instantiate Controller objects to access their Handlers
        MovieController movieController = new MovieController();
        CustomerController customerController = new CustomerController();
        RentalController rentalController = new RentalController();

        // Movie Handlers
        // GET request to http://localhost:3000/movies-list for all listed movies *
        app.get("/movies-list", movieController.getAllMoviesHandler);

        // GET request to http://localhost:3000/movie/{id} for a specific movie listed by ID *
        app.get("/movie/{id}", movieController.getMovieByIdHandler);

        // POST request to http://localhost:3000/add-movie to add a new movie *
        app.post("/add-movie", movieController.addNewMovieHandler);

        // Customer Handlers
        // GET request to http://localhost:3000/customers-list for all listed customers *
        app.get("/customers-list", customerController.getAllCustomersHandler);

        // GET request to http://localhost:3000/customer/{id} for a specific customer listed by ID *
        app.get("/customer/{id}", customerController.getCustomerByIdHandler);

        // POST request to http://localhost:3000/add-customer to add a new customer *
        app.post("/add-customer", customerController.addNewCustHandler);

        // PATCH request to http://localhost:3000/update-custEmail/{id} *
        app.patch("/update-custEmail/{id}", customerController.updateCustomerEmail);

        // Rental Records Handlers
        // GET request to http://localhost:3000/rental-records for all rental records *
        app.get("/rental-records", rentalController.getAllRentalsHandler);

        // GET request to http://localhost:3000/cust-rentals/{id} for rental records for a specific customer *
        app.get("/cust-rentals/{id}", rentalController.getRentalsByCustIdHandler);

        // GET request to http://localhost:3000/movie-rentals/{id} for rental records for a specific movie *
        app.get("/movie-rentals/{id}", rentalController.getRentalsByMovieIdHandler);

        // POST request to http://localhost:3000/add-rental to add a new rental record *
        app.post("/add-rental", rentalController.addNewRentalHandler);

        // DELETE request to http://localhost:3000/remove-rental/{id} for specific rental record by ID *
        app.delete("/remove-rental/{id}", rentalController.removeRentalsHandler);

    }
}
