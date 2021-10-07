package org.prgrms.orderApp.customer;

import org.prgrms.orderApp.util.library.Validate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.prgrms.orderApp.util.library.Validate.*;

// Entity
public class Customer implements CustomerModel{
    private final UUID customerId;
    private final String email;
    private String name;
    private LocalDateTime lastLoginAt, createdAt;

    public Customer(UUID customerId, String email){
        Assert.isTrue(checkAddress(email), "Invalid email address");

        this.customerId = customerId;
        this.email = email;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt){
        Assert.notNull(name, "이름은 null 값을 허용하지 않습니다. 확인 바랍니다.");
        Assert.isTrue(name.length() >= 1 , "이름은 1 자 이상입니다. 확인 바랍니다.");
        Assert.isTrue(checkAddress(email), "Invalid email address");

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt){
        Assert.notNull(name, "이름은 null 값을 허용하지 않습니다. 확인 바랍니다.");
        Assert.isTrue(name.length() >= 1 , "이름은 1 자 이상입니다. 확인 바랍니다.");
        Assert.isTrue(checkAddress(email), "Invalid email address");

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }


    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeName(String name){
        Assert.isTrue(checkAddress(email), "Invalid email address");
        this.name = name;
    }

    public void login(){
        this.lastLoginAt = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "Customer {" +
                "customerId=" + customerId +
                ", name=" + name +
                ", email=" + email +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                "}";
    }


}
