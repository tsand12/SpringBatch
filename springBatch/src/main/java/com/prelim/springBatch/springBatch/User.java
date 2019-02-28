package com.prelim.springBatch.springBatch;


public class User {


    private Long id;

    private String firstName;

    private String lastName;
    // private String authToken;

    private int age;
    //private Date birthday;

    public User() {}

    public User(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
