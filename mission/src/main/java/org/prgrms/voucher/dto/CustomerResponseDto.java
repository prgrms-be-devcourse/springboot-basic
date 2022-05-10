package org.prgrms.voucher.dto;

import org.prgrms.voucher.models.Customer;

import java.time.LocalDateTime;

public class CustomerResponseDto {

    private final Long customerId;
    private final String customerName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CustomerResponseDto(Long customerId, String customerName, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CustomerResponseDto from(Customer customer) {

        return new CustomerResponseDto(customer.getCustomerId(), customer.getCustomerName(),
                customer.getCreatedAt(), customer.getUpdatedAt());
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}