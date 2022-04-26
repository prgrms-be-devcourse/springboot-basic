package org.prgrms.spring_week1.customer.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private String address;
    private Gender gender;
    private PhoneNumber phoneNumber;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(UUID customerId, String name, String address,
        Gender gender, PhoneNumber phoneNumber) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Customer(UUID customerId, String name, String address,
        Gender gender, PhoneNumber phoneNumber, LocalDateTime createdAt,
        LocalDateTime updatedAt) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void setAddress(String address) {
        this.address = address;
        this.updatedAt = LocalDateTime.now();

    }

    public void setGender(Gender gender) {
        this.gender = gender;
        this.updatedAt = LocalDateTime.now();

    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now();

    }
}
