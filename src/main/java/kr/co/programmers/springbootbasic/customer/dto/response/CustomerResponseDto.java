package kr.co.programmers.springbootbasic.customer.dto.response;

import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;

import java.util.UUID;

public class CustomerResponseDto {
    private final UUID id;
    private final String name;
    private final CustomerStatus status;

    public CustomerResponseDto(UUID id, String name, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}
