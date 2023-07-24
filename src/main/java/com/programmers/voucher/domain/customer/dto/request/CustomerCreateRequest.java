package com.programmers.voucher.domain.customer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CustomerCreateRequest {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9._%+-]{1,20}@[A-Za-z0-9.-]{1,20}\\.[A-Za-z]{2,3}$")
    private String email;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]{1,20}")
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
