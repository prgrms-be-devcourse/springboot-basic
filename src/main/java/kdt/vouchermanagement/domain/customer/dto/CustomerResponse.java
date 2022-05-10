package kdt.vouchermanagement.domain.customer.dto;

import kdt.vouchermanagement.domain.customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerResponse {
    private long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private CustomerResponse(long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    public static List<CustomerResponse> listOf(List<Customer> customers) {
        return customers.stream()
                .map(CustomerResponse::of)
                .collect(Collectors.toList());
    }
}
