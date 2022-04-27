package com.programmers.springbootbasic.dto;

import java.time.LocalDateTime;

public class CustomerDTO {

    private String customerId;
    private String name;
    private LocalDateTime registrationDate;

    public CustomerDTO(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public CustomerDTO(String id, String name, LocalDateTime registrationDate) {
        this.customerId = id;
        this.name = name;
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
