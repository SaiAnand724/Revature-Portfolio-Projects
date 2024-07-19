package com.revature.services;

import com.revature.DAO.RentalDAO;
import com.revature.models.Customer;
import com.revature.models.Rental;

import java.util.ArrayList;

public class RentalServices {

    // Instantiate RentalDAO for its methods
    RentalDAO rentalDAO = new RentalDAO();

    // Get all rentals method for services
    public ArrayList<Rental> getAllRentals(){
        return rentalDAO.getAllRentals();
    }

    // Get rentals for specific customer by id
    public ArrayList<Rental> getRentalsByCustId(int cust_id){
        return rentalDAO.getRentalsByCustId(cust_id);
    }

    // Get rentals for specific movie by id
    public ArrayList<Rental> getRentalsByMovieId(int movie_id){
        return rentalDAO.getRentalByMovieId(movie_id);
    }

    // Add rental method for services
    public Rental addRental(Rental rental) throws IllegalArgumentException{

        // Positive ID check for foreign keys
        if(rental.getCust_id_fk() <= 0){
            throw new IllegalArgumentException("Customer FK IDs must be positive");
        }

        if(rental.getMovie_id_fk() <= 0){
            throw new IllegalArgumentException("Movie FK IDs must be positive");
        }

        return rentalDAO.addRentalRecord(rental);
    }

    // Remove rental method for services
    public void removeRental(int rental_id) throws IllegalArgumentException {
        // Positive ID check
        if(rental_id <= 0){
            throw new IllegalArgumentException("Rental IDs must be positive");
        }

        ArrayList<Rental> rentalList = rentalDAO.getAllRentals();

        // Rental ID in rentalList check
        boolean rentalFound = false;

        // Check if the rental ID exists in the list
        for (Rental rentalObj : rentalList) {
            if (rentalObj.getRental_id() == rental_id) {
                rentalFound = true;
                break; // ID found, no need to continue the loop
            }
        }

        if (!rentalFound) {
            throw new IllegalArgumentException("Rental ID does not exist in DB");
        }

        // Proceed with the deletion if the rental ID exists
        // Call removeRentalRecord method from DAO - void so no return type
        rentalDAO.removeRentalRecord(rental_id);}
}
