package com.prgrms.vouchermanagement.customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerResponse {

    private Long customerId;
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public CustomerResponse(Long customerId, String name, String email, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getCreatedAt());
    }

    public static List<CustomerResponse> fromList(List<Customer> customers) {
        List<CustomerResponse> customerResponses = new ArrayList<>();
        customers.forEach(customer -> customerResponses.add(CustomerResponse.from(customer)));
        return customerResponses;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }
}
