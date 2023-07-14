package org.weekly.weekly.customer.dto.response;

import org.weekly.weekly.customer.domain.Customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public class CustomerResponse {
    String name;
    String email;
    LocalDateTime createAt;

    private CustomerResponse(String name, String email, LocalDateTime createAt) {
        this.name = name;
        this.email = email;
        this.createAt = createAt;
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(customer.getName(), customer.getEmail(), customer.getCreateAt());
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getName() {
        return name;
    }

    public String result() {
        return MessageFormat.format("[이름: {0}, 이메일: {1}, 생성 시기: {2}]", name, email, createAt);
    }
}
