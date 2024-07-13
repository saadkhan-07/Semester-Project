package com.example.tailormanagementsystem;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class User implements Serializable{
    private String username;
    private String password;
    private final int resetcode=164;
  User()
  {}


    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public int getResetcode() {
        return resetcode;
    }

    @Override
    public java.lang.String toString() {
        return  "username='" + username + '\'' +
                ", password='" + password + '\'';

    }

    public static void main(String[] args) throws IOException {
User user=UserAPi.readUser();
System.out.println(user);
    }

    @Override
    public boolean equals(Object obj) {
       User user=(User)obj;
        return (this.getUsername().equals(user.getUsername()) && this.getPassword().equals(user.getPassword()));
    }
}
