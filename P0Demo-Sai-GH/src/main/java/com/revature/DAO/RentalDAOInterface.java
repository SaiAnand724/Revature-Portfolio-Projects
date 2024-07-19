package com.revature.DAO;

import com.revature.models.Rental;

import java.util.ArrayList;

public interface RentalDAOInterface {

    ArrayList<Rental> getAllRentals();

    Rental addRentalRecord(Rental rental);

    void removeRentalRecord(int rental_id);

    ArrayList<Rental> getRentalsByCustId(int cust_id);

    ArrayList<Rental> getRentalByMovieId(int cust_id);
}
