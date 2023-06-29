package kr.co.programmers.springbootbasic.dto;

import kr.co.programmers.springbootbasic.customer.CustomerStatus;

public class CustomerResponseDto {
    private final long id;
    private final String name;
    private final CustomerStatus status;

    public CustomerResponseDto(long id, String name, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerStatus getStatus() {
        return status;
    }
}
