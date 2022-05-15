package com.example.zeitplan_proyect.model;

import java.util.ArrayList;
import java.util.HashSet;

public class User {
    String name, id, password, email;
    public static User instance;



    public User(){

    }
    public User(String name, String id, String password, String email) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;

    }

    public static User getInstance(String name, String id, String email, String password){
        if(instance == null){
            instance = new User(name, id , email, password);
        }
        return instance;
    }
    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
