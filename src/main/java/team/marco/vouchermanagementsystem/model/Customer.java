package team.marco.vouchermanagementsystem.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String email;
    private final LocalDateTime createdAt;

    private String name;
    private LocalDateTime lastLoginAt;

    public Customer(UUID id, String name, String email, LocalDateTime createdAt) {
        validateName(name);

        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    public Customer(UUID id, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        validateName(name);

        this.id = id;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public void changeName(String name) {
        validateName(name);

        this.name = name;
    }

    public void login() {
        this.lastLoginAt = LocalDateTime.now();
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

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
