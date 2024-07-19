package com.revature.DAO;

import com.revature.models.Customer;
import com.revature.models.Movie;
import com.revature.models.Rental;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class RentalDAO implements RentalDAOInterface{
    @Override
    public ArrayList<Rental> getAllRentals() {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "select * from rentals";

            // Statement object for 'select *' query - used because it needs no variables
            Statement statement = conn.createStatement();

            // Execute query and save results into a ResultSet
            ResultSet resultSet = statement.executeQuery(sql);

            // ArrayList variable instantiated to hold the rental records
            ArrayList<Rental> rentalsList = new ArrayList<>();

            // Iterate through ResultSet to add to ArrayList of rental records
            while(resultSet.next()){
                // Using getCustomerById and getMovieById methods for getting Customer and Movie objects
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerById(resultSet.getInt("cust_id_fk"));

                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(resultSet.getInt("movie_id_fk"));

                // Instantiate each Rental object
                Rental rental = new Rental(
                        resultSet.getInt("rental_id"),
                        customer,
                        movie,
                        resultSet.getInt("days_rented")
                );

                // Add each rental record object to ArrayList
                rentalsList.add(rental);
            }

            return rentalsList;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to get rental records!");
        }
        return null;
    }

    @Override
    public Rental addRentalRecord(Rental rental) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "insert into rentals (cust_id_fk, movie_id_fk, days_rented) values (?, ?, ?)";

            //PreparedStatement object for filling in wildcards
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, rental.getCust_id_fk());
            preparedStatement.setInt(2, rental.getMovie_id_fk());
            preparedStatement.setInt(3, rental.getDays_rented());

            // Execute query and save to DB
            preparedStatement.executeUpdate();

            return rental;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to add to rental records!");
        }
        return null;
    }

    @Override
    public void removeRentalRecord(int rental_id) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "delete from rentals where rental_id = ?";

            // PreparedStatement object for 'where rental_id = ?' query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            // Use movie_id parameter to fill in wildcard
            preparedStatement.setInt(1, rental_id);

            // Execute query to delete
            preparedStatement.execute();

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to remove rental record!");
        }
    }

    @Override
    public ArrayList<Rental> getRentalsByCustId(int cust_id) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "select * from rentals where cust_id_fk = ?";

            // PreparedStatement object for 'where cust_id_fk = ?' query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            // Use cust_id parameter to fill in wildcard
            preparedStatement.setInt(1, cust_id);

            // Execute query and save results into a ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // ArrayList variable instantiated to hold the rental records
            ArrayList<Rental> custRentalsList = new ArrayList<>();

            // Iterate through ResultSet to add to ArrayList of rental records
            while(resultSet.next()){
                // Using getCustomerById and getMovieById methods for getting Customer and Movie objects
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerById(resultSet.getInt("cust_id_fk"));

                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(resultSet.getInt("movie_id_fk"));

                // Instantiate each Rental object
                Rental rental = new Rental(
                        resultSet.getInt("rental_id"),
                        customer,
                        movie,
                        resultSet.getInt("days_rented")
                );

                // Add each rental record object to ArrayList
                custRentalsList.add(rental);
            }

            return custRentalsList;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to get requested rental records for customer!");
        }
        return null;
    }

    @Override
    public ArrayList<Rental> getRentalByMovieId(int movie_id) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "select * from rentals where movie_id_fk = ?";

            // PreparedStatement object for 'where movie_id_fk = ?' query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            // Use movie_id parameter to fill in wildcard
            preparedStatement.setInt(1, movie_id);

            // Execute query and save results into a ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // ArrayList variable instantiated to hold the rental records
            ArrayList<Rental> movieRentalsList = new ArrayList<>();

            // Iterate through ResultSet to add to ArrayList of rental records
            while(resultSet.next()){
                // Using getCustomerById and getMovieById methods for getting Customer and Movie objects
                CustomerDAO customerDAO = new CustomerDAO();
                Customer customer = customerDAO.getCustomerById(resultSet.getInt("cust_id_fk"));

                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(resultSet.getInt("movie_id_fk"));

                // Instantiate each Rental object
                Rental rental = new Rental(
                        resultSet.getInt("rental_id"),
                        customer,
                        movie,
                        resultSet.getInt("days_rented")
                );

                // Add each rental record object to ArrayList
                movieRentalsList.add(rental);
            }

            return movieRentalsList;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to get requested rental records for movie!");
        }
        return null;
    }
}
