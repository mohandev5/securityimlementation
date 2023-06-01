package com.securirt1.dto;

public class AuthRequests {



    private String name;
    private String password;

    public AuthRequests(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public AuthRequests() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
