package com.revature.DAO;

import com.revature.models.Customer;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO implements CustomerDAOInterface{

    @Override
    public ArrayList<Customer> getAllCustomers() {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "select * from customers";

            // Statement object for 'select *' query - used because it needs no variables
            Statement statement = conn.createStatement();

            // Execute query and save results into a ResultSet
            ResultSet resultSet = statement.executeQuery(sql);

            // ArrayList variable instantiated to hold the Customer records
            ArrayList<Customer> customersList = new ArrayList<>();

            // Iterate through ResultSet to add to ArrayList of rental records
            while(resultSet.next()){
                // Instantiate Customer object found in ResultSet using all args constructor
                Customer customer = new Customer(
                        resultSet.getInt("cust_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );

                // Add each rental record object to ArrayList
                customersList.add(customer);
            }

            return customersList;

            } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to get customer list!");
        }
        return null;
    }

    @Override
    public Customer getCustomerById(int cust_id) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "select * from customers where cust_id = ?";

            // PreparedStatement object for 'where id = ?' query
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            // Use cust_id parameter to fill in wildcard
            preparedStatement.setInt(1, cust_id);

            // Execute query and save results into a ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            // While loop to extract data
            while(resultSet.next()){
                // Instantiate Customer object found in ResultSet using all args constructor
                Customer customer = new Customer(
                        resultSet.getInt("cust_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );

                // Returns Customer object
                return customer;

            }


        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to get requested customer by ID!");
        }
        return null;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            // SQL query string
            String sql = "insert into customers (name, email) values (?, ?)";

            //PreparedStatement object for filling in wildcards
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, customer.getCust_name());
            preparedStatement.setString(2, customer.getCust_email());

            // Execute query and save to DB
            preparedStatement.executeUpdate();

            return customer;


        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to add new customer!");
        }
        return null;
    }

    @Override
    public String updateCustomerEmail(int cust_id, String newEmail) {
        // Try-catch block for Postgres connection
        try(Connection conn = ConnectionUtil.getConnection()){
            //Create our SQL String
            String sql = "update customers set email = ? where cust_id = ?";

            //Create a PreparedStatement to fill in the wildcards
            PreparedStatement ps = conn.prepareStatement(sql);

            //ps.setXYZ methods to fill in the wildcards
            ps.setString(1, newEmail);
            ps.setInt(2, cust_id);

            //execute the update!
            ps.executeUpdate();

            //if successful, return the new salary!
            return newEmail;

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to update existing customer!");
        }
        return null;
    }
}
