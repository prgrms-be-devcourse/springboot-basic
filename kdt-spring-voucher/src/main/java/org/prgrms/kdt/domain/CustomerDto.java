package org.prgrms.kdt.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CustomerDto {

    private final UUID customerId;
    private final String name;
    private final String email;
    private final LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
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
