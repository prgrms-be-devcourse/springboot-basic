package org.prgrms.kdt.domain;

public class Customer {
    private long id;
    private String email;

    public Customer() {
    }

    public Customer(String email) {
        this.email = email;
    }

    public Customer(long id, String email) {
        this.id = id;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return "<ID : " + id + " , Email : " + email + " >";
    }
}
