package com.revature.DAO;

import com.revature.models.Movie;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class MovieDAO implements MovieDAOInterface {

    @Override
    public ArrayList<Movie> getAllMovies() {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "select * from movies";

            // Statement object for 'select *' query - used because it needs no variables
            Statement statement = conn.createStatement();

            // Execute query and save results into a ResultSet
            ResultSet resultSet = statement.executeQuery(sql);

            // ArrayList variable instantiated to hold the movies
            ArrayList<Movie> moviesList = new ArrayList<>();

            // Iterate through ResultSet to add to ArrayList of movies
            while(resultSet.next()){
                // Instantiate Movie object found in ResultSet using all args constructor
                Movie movie = new Movie(
                        resultSet.getInt("movie_id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getString("release_year"),
                        resultSet.getDouble("rental_price")
                );

                // Add each Movie object to ArrayList
                moviesList.add(movie);
            }

            // Return the ArrayList of Movies
            return moviesList;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to get movie list!");
        }
        return null;
    }

    @Override
    public Movie getMovieById(int movie_id) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "select * from movies where movie_id = ?";

            // PreparedStatement object for 'where movie_id = ?' query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            // Use movie_id parameter to fill in wildcard
            preparedStatement.setInt(1, movie_id);

            // Execute query and save results into a ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // While loop to extract data
            while(resultSet.next()){
                // Instantiate Movie object found in ResultSet using all args constructor
                Movie movie = new Movie(
                        resultSet.getInt("movie_id"),
                        resultSet.getString("title"),
                        resultSet.getString("genre"),
                        resultSet.getString("release_year"),
                        resultSet.getDouble("rental_price")
                );

                // Returns Movie object
                return movie;

            }

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to get requested movie info!");
        }
        return null;
    }

    @Override
    public Movie addMovie(Movie movie) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "insert into movies (title, genre, release_year, rental_price) values (?, ?, ?, ?)";

            //PreparedStatement object for filling in wildcards
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, movie.getMovie_title());
            preparedStatement.setString(2, movie.getMovie_genre());
            preparedStatement.setString(3, movie.getMovie_year());
            preparedStatement.setDouble(4, movie.getMovie_price());

            // Execute query and save to DB
            preparedStatement.executeUpdate();

            return movie;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to add new movie!");
        }
        return null;
    }

}
