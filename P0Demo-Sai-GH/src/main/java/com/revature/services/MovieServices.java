package com.revature.services;

import com.revature.DAO.MovieDAO;
import com.revature.models.Movie;

import java.util.ArrayList;

public class MovieServices {
    // Instantiate MovieDAO for its methods
    MovieDAO movieDAO = new MovieDAO();

    // Get all movies method for services
    public ArrayList<Movie> getAllMovies(){
        return movieDAO.getAllMovies();
    }

    // Get movie by movie_id
    public Movie getMovieById(int movie_id) throws IllegalArgumentException{
        // Positive ID check
        if(movie_id <= 0){
            throw new IllegalArgumentException("Movie IDs must be positive");
        }

        return movieDAO.getMovieById(movie_id);
    }

    // Add new movie to DB
    public Movie addMovie(Movie movie) throws IllegalArgumentException{

        if(movie.getMovie_price() < 1.99){
            throw new IllegalArgumentException("Movie prices must be above $2");
        }

        movieDAO.addMovie(movie);

        return movie;
    }
}
