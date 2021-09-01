package org.prgrms.kdt.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@Setter
public class CustomerEntity {
    private final UUID customerId;
    private Optional<String> name;
    private final Optional<String> email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public CustomerEntity(UUID customerId, Optional<String> name, Optional<String> email, LocalDateTime createdAt) {
        validateName(name.get());
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
    }

    public CustomerEntity(UUID customerId, Optional<String> name, Optional<String> email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }


    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public void changeName(String name) {
        validateName(name);
        this.name = Optional.of(name);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}
