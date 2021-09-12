package org.prgrms.kdt.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class CustomerDto {

    private UUID customerId;
    private String name;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime lastLoginAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime createdAt;

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

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static CustomerEntity to(CustomerDto dto){
        return new CustomerEntity(dto.customerId,
                dto.name,
                dto.email,
                dto.lastLoginAt,
                dto.createdAt);
    }


}
