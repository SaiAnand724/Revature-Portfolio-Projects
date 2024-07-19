package com.revature.DAO;

import com.revature.models.Movie;

import java.util.ArrayList;

public interface MovieDAOInterface {

    ArrayList<Movie> getAllMovies();

    Movie getMovieById(int movie_id);

    Movie addMovie(Movie movie);

}
