package team.marco.voucher_management_system.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;

    private Customer(UUID id, String name, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static class Builder {
        private UUID id = UUID.randomUUID();
        private String name;
        private String email;
        private LocalDateTime createdAt = LocalDateTime.now();

        public Builder(String name, String email) {
            validateStringNotBlank(name);
            validateStringNotBlank(email);
            this.name = name;
            this.email = email;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Customer build() {
            return new Customer(id, name, email, createdAt);
        }
    }

    private static void validateStringNotBlank(String name) {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("빈 문자열 일 수 없습니다.");
    }
}
