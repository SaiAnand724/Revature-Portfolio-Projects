package com.revature.models;

public class Customer {

    // Class fields - modeled after database fields
    private int cust_id;
    private String cust_name;
    private String cust_email;

    // No args constructor
    public Customer() {
    }

    // All-args constructor
    public Customer(int cust_id, String cust_name, String cust_email) {
        this.cust_id = cust_id;
        this.cust_name = cust_name;
        this.cust_email = cust_email;
    }

    // Getters and Setters
    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "cust_id=" + cust_id +
                ", cust_name='" + cust_name + '\'' +
                ", cust_email='" + cust_email + '\'' +
                '}';
    }
}
