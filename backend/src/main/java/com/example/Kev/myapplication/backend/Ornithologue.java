package com.example.Kev.myapplication.backend;

import com.googlecode.objectify.annotation.Id;

/**
 * Created by Kev on 25.04.2017.
 */

public class Ornithologue {


    @Id
    private int id;
    private String username;
    private String password;
    private String age;
    private String canton;

    public Ornithologue(String username, String password, String age, String canton) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.canton = canton;
    }

    public Ornithologue(){}

    public Ornithologue(int id, String username, String password, String age, String canton) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.canton = canton;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }
}
