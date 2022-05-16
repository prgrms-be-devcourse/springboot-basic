package com.programmers.springbootbasic.domain;

import java.time.LocalDateTime;

public class Customer {

    private String customerId;
    private String name;
    private LocalDateTime registrationDate;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public Customer(String id, String name, LocalDateTime registrationDate) {
        this(id, name);
        this.registrationDate = registrationDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return  "[회원 ID: " + customerId + "], [회원이름: " + name + "], [가입날짜: " + registrationDate + "]";
    }

}
