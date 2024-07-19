package com.revature.models;

public class Movie {

    // Class fields - modeled after database fields
    private int movie_id;
    private String movie_title;
    private String movie_genre;
    private String movie_year;
    private double movie_price;

    // No-args constructor
    public Movie() {
    }

    // All-args constructor
    public Movie(int movie_id, String movie_title, String movie_genre, String movie_year, double movie_price) {
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.movie_genre = movie_genre;
        this.movie_year = movie_year;
        this.movie_price = movie_price;
    }

    // Getters and Setters
    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_genre() {
        return movie_genre;
    }

    public void setMovie_genre(String movie_genre) {
        this.movie_genre = movie_genre;
    }

    public String getMovie_year() {
        return movie_year;
    }

    public void setMovie_year(String movie_year) {
        this.movie_year = movie_year;
    }

    public double getMovie_price() {
        return movie_price;
    }

    public void setMovie_price(double movie_price) {
        this.movie_price = movie_price;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movie_id=" + movie_id +
                ", movie_title='" + movie_title + '\'' +
                ", movie_genre='" + movie_genre + '\'' +
                ", movie_year=" + movie_year +
                ", movie_price=" + movie_price +
                '}';
    }
}
