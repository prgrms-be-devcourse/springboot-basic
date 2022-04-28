package org.prgrms.springbootbasic.dto;

public class CustomerDto {

    private final String customerId;
    private final String email;
    private final String name;

    public CustomerDto(String customerId, String email, String name) {
        this.customerId = customerId;
        this.email = email;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
