package team.marco.voucher_management_system.controller.customer.dto;

import team.marco.voucher_management_system.domain.customer.Customer;

import java.time.LocalDateTime;

public class CustomerResponse {
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public CustomerResponse(String name, String email, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(customer.getName(), customer.getEmail(), customer.getCreatedAt());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
