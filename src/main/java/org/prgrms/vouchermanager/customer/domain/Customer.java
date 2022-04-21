package org.prgrms.vouchermanager.customer.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String email;
    private final LocalDateTime createAt;
    private final String name;

    public Customer(UUID customerId, String name, String email, LocalDateTime createAt) {
        validateCustomerConstructorArgs(customerId, name, email, createAt);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createAt = createAt.truncatedTo(ChronoUnit.SECONDS);
    }

    private void validateCustomerConstructorArgs(UUID customerId, String name, String email, LocalDateTime createAt) {
        validateID(customerId);
        validateName(name);
        validateEmail(email);
        validateCreateAt(createAt);
    }

    private void validateCreateAt(LocalDateTime createAt) {
        if (createAt == null) throw new IllegalArgumentException("createAt은 null이 될 수 없습니다.");
    }

    private void validateID(UUID customerId) {
        if (customerId == null) throw new IllegalArgumentException("customerId는 null이 될 수 없습니다.");
    }

    private void validateEmail(String email) {
        if (email.isBlank()) throw new IllegalArgumentException("email은 공백이 될 수 없습니다.");
        if (!email.contains("@")) throw new IllegalArgumentException("email은 @를 포함해야 합니다.");
    }

    private void validateName(String name) {
        if (name.isBlank()) throw new IllegalArgumentException("name은 공백이 될 수 없습니다.");
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId.equals(customer.customerId) && email.equals(customer.email) && createAt.equals(customer.createAt) && name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, email, createAt, name);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
