package com.revature.controllers;

import com.revature.models.Movie;
import com.revature.services.MovieServices;
import io.javalin.http.Handler;

import java.util.ArrayList;

public class MovieController {

    // Movie service object
    MovieServices movieServices = new MovieServices();

    // Handler for GET requests for "/movies-list
    public Handler getAllMoviesHandler = ctx -> {
        // ArrayList of rentals, populated by getAllMovies service method
        ArrayList<Movie> movies = movieServices.getAllMovies();

        // Sending HTTP Response in JSON to endpoint
        ctx.json(movies);

        // HTTP Response status code -> OK
        ctx.status(200);
    };

    // Handler for GET requests for "/movie/{id}"
    public Handler getMovieByIdHandler = ctx -> {
        // Extract path parameter {id} from HTTP Request
        int movie_id = Integer.parseInt(ctx.pathParam("id"));

        // Instantiate a Movie object using DAO method and movie_id from above
        Movie movie = movieServices.getMovieById(movie_id);

        if(movie == null){
            ctx.status(400); // Bad Request -> 400
            ctx.result("Movie not found!");
        } else {
            ctx.status(200); // OK -> 200
            ctx.json(movie); // Send the Movie back as JSON
        }

    };

    // Handler for POST requests to "/add-movie"
    public Handler addNewMovieHandler = ctx -> {
        // Format incoming JSON data into Class object for DAO class using ctx.bodyAsClass()
        Movie newMovie = ctx.bodyAsClass(Movie.class);

        try {
            //Send new object to the service to be inserted into the DB
            Movie addedMovie = movieServices.addMovie(newMovie);
            ctx.status(201); // Resource created -> 201
            ctx.json(addedMovie); // Send added Object back to user as JSON
        } catch (IllegalArgumentException e){
            ctx.status(400); // Bad Request -> 400
            ctx.result(e.getMessage()); // Send back specific error message
        }
    };
}
