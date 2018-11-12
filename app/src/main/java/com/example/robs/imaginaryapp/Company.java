package com.example.robs.imaginaryapp;

/**
 * Created by Robs on 11.11.18.
 */

public class Company {

    // class for the Company

    private String companyName = "";
    private String street = "";
    private String country = "";
    private String phone = "";

    public Company(String companyName, String street, String country, String phone) {
        this.companyName = companyName;
        this.street = street;
        this.country = country;
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
