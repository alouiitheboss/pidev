package com.example.aloui.myfianlapplication.Entities;

/**
 * Created by aloui on 27/11/2017.
 */

public class User {
    private int Id;
    private String email;
    private String username;
    private String password;

    public User() {
    }

    public User(int id, String email, String username, String password) {
        Id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(int id,String email, String username) {
        this.Id = id;
        this.email = email;
        this.username = username;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
