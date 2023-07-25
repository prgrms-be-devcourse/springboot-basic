package com.programmers.voucher.domain.customer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerCreateRequest {
    @NotNull(message = "Email must not be null")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]{1,20}@[A-Za-z0-9.-]{1,20}\\.[A-Za-z]{2,3}$",
            message = "Email username 20 chars or less and domain name 20 chars or less")
    private String email;

    @NotNull(message = "Name must not be null")
    @Pattern(regexp = "^[A-Za-z0-9]{1,20}", message = "Name 20 chars or less")
    private String name;

    public CustomerCreateRequest() {
    }

    public CustomerCreateRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
