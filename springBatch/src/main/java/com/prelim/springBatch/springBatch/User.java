package com.prelim.springBatch.springBatch;


import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    private String first_name;

    private String last_name;
    // private String authToken;

    private int age;
    //private Date birthday;

    public User(){}

    public User(String first_name, String last_name, int age) {
        this.first_name = first_name;
        this.last_name = last_name;
        // this.authToken = authToken;
        this.age = age;
        //this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /*public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }*/

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
