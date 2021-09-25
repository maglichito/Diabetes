package com.example.diabetes.data;

public class User {
    private int id;
    private String name;
    private String lastname;
    private String email;
    private String type;
    private String since;
    private String country;
    private String token;
    private String ads;

    public User(int id, String name, String lastname, String email, String type, String since, String country, String ads,String token) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.type = type;
        this.since = since;
        this.country = country;
        this.ads = ads;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }


    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getSince() {
        return since;
    }

    public String getCountry() {
        return country;
    }

    public String getAds() {
        return ads;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSince(String since) {
        this.since = since;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }

    public void setToken(String token) {
        this.token = token;
    }
}



