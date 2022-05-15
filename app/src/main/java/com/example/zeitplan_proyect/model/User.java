package com.example.zeitplan_proyect.model;

public class User {
    public String id,name, email;
    private static User instance = null;
    private User() {

    }

    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new User();
        }
    }

    public static User getInstance(){

        if (instance == null) createInstance();
        return instance;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
