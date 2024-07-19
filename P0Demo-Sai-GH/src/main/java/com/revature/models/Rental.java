package com.revature.models;

public class Rental {

    private int rental_id;

    // Rentals will have access to full Customer object data
    private Customer customer;
    private int cust_id_fk;

    // Rentals will have access to full Movie object data
    private Movie movie;
    private int movie_id_fk;

    private int days_rented;

    // No-args constructor
    public Rental() {
    }

    // All-args constructor - Class variables
    public Rental(int rental_id, Customer customer, Movie movie, int days_rented) {
        this.rental_id = rental_id;
        this.customer = customer;
        this.movie = movie;
        this.days_rented = days_rented;
    }

    // Foreign key constructor - No id as it will autogenerate due to 'serial' constraint
    public Rental(int cust_id_fk, int movie_id_fk, int days_rented) {
        this.cust_id_fk = cust_id_fk;
        this.movie_id_fk = movie_id_fk;
        this.days_rented = days_rented;
    }

    // Getters and Setters
    public int getRental_id() {
        return rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id = rental_id;
    }

    public int getDays_rented() {
        return days_rented;
    }

    public void setDays_rented(int days_rented) {
        this.days_rented = days_rented;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCust_id_fk() {
        return cust_id_fk;
    }

    public void setCust_id_fk(int cust_id_fk) {
        this.cust_id_fk = cust_id_fk;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getMovie_id_fk() {
        return movie_id_fk;
    }

    public void setMovie_id_fk(int movie_id_fk) {
        this.movie_id_fk = movie_id_fk;
    }

    // toString
    @Override
    public String toString() {
        return "Rental{" +
                "rental_id=" + rental_id +
                ", customer=" + customer +
                ", movie=" + movie +
                ", days_rented=" + days_rented +
                '}';
    }
}
