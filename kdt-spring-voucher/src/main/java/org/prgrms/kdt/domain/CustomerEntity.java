package org.prgrms.kdt.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@Setter
public class CustomerEntity {
    private final UUID customerId;
    private String name;
    private final String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime lastLoginAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime createdAt;

    public CustomerEntity(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.name = name;
        this.customerId = customerId;
        this.email = email;
        this.createdAt = createdAt;
    }

    public CustomerEntity(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
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
        this.name = name;
    }
}
