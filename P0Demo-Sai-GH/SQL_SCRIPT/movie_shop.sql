-- P0Demo-Sai SQL Script for movie rental store with
-- a movies list, customer list, and rental records

-- Create tables
-- Table for movies
create table movies (
    movie_id SERIAL primary key,
    title VARCHAR(255) not null,
    genre VARCHAR(100),
    release_year VARCHAR(100),
    rental_price DECIMAL(10, 2) not null
);

-- Table for customers
create table customers (
    cust_id SERIAL primary key,
    name VARCHAR(100) not null,
    email VARCHAR(100) unique not null
);

-- Table for rentals
create table rentals (
    rental_id SERIAL primary key,
    cust_id_fk INTEGER references customers(cust_id),
    movie_id_fk INTEGER references movies(movie_id),
    days_rented INTEGER not null
);

-- Insert example data into movies
INSERT INTO movies (title, genre, release_year, rental_price) VALUES
('The Shawshank Redemption', 'Drama', 1994, 3.99),
('The Godfather', 'Crime', 1972, 4.99),
('The Dark Knight', 'Action', 2008, 3.99),
('Pulp Fiction', 'Crime', 1994, 3.99),
('Inception', 'Sci-Fi', 2010, 4.49),
('Fight Club', 'Drama', 1999, 3.99),
('Forrest Gump', 'Drama', 1994, 3.99);

-- Insert example data into customers
INSERT INTO customers (name, email) VALUES
('Alice Johnson', 'alice.johnson@example.com'),
('Bob Smith', 'bob.smith@example.com'),
('Charlie Brown', 'charlie.brown@example.com'),
('David Wilson', 'david.wilson@example.com'),
('Eve Adams', 'eve.adams@example.com'),
('Frank White', 'frank.white@example.com'),
('Grace Lee', 'grace.lee@example.com');

-- Insert example data into rentals
INSERT INTO rentals (cust_id_fk, movie_id_fk, days_rented) VALUES
(1, 1, 3),
(2, 2, 2),
(1, 3, 1),
(2, 4, 4),
(3, 5, 5),
(4, 6, 2),
(5, 7, 3);