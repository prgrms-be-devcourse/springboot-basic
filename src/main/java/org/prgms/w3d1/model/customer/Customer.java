package org.prgms.w3d1.model.customer;

import org.prgms.w3d1.model.wallet.VoucherWallet;
import org.prgms.w3d1.model.wallet.Wallet;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = convertLocalDateForWindows(createdAt);
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = convertLocalDateForWindows(lastLoginAt);
        this.createdAt = convertLocalDateForWindows(createdAt);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeName(String name){
        validateName(name);
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (!Objects.equals(customerId, customer.customerId)) return false;
        if (!Objects.equals(name, customer.name)) return false;
        if (!Objects.equals(email, customer.email)) return false;
        if (!Objects.equals(lastLoginAt, customer.lastLoginAt))
            return false;
        return Objects.equals(createdAt, customer.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, email, lastLoginAt, customerId);
    }

    private void validateName(String name) {
        if(name.isBlank()){
            throw new RuntimeException("Name should not be blank");
        }
    }

    private LocalDateTime convertLocalDateForWindows(LocalDateTime localDateTime){
        if(localDateTime == null){
            return null;
        }
        return localDateTime.truncatedTo(ChronoUnit.MILLIS);
    }
}
