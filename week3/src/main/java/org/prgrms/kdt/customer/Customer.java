package org.prgrms.kdt.customer;

import lombok.Getter;
import org.prgrms.kdt.wallet.Wallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;
    private List<Wallet> wallets;

    public Customer(UUID customerId,String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void changeName(String name){
        validateName(name);
        this.name = name;
    }

    public void login(){
        this.lastLoginAt = LocalDateTime.now();
    }

    private void validateName(String name) {
        if(name.isBlank()){
            throw new RuntimeException("Name should not be blank");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginAt=" + lastLoginAt +
                ", createdAt=" + createdAt +
                '}';
    }

}
