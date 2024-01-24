package com.example.tradehub.pojo;

import java.util.List;

public class UserModel {
    private String userId;
    String email;
    private String username;
    private String profile_pic;

    private List<String> listings;
    String location;
    double avg_rating;
public UserModel()
{}
    public UserModel(String userId, String username, String profile_pic, String email, List<String> listings, String location, double avg_rating) {
        this.userId = userId;
        this.username = username;
        this.profile_pic = profile_pic;
        this.email = email;
        this.listings = listings;
        this.location = location;
        this.avg_rating = avg_rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getListings() {
        return listings;
    }

    public void setListings(List<String> listings) {
        this.listings = listings;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }
    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}


