package org.prgms.kdtspringweek1.controller.dto;

import org.prgms.kdtspringweek1.customer.entity.Customer;

import java.util.UUID;

public class UpdateCustomerRequestDto {
    private final UUID customerId;
    private final String name;
    private final boolean isBlackCustomer;

    public UpdateCustomerRequestDto(UUID customerId, String name, boolean isBlackCustomer) {
        this.customerId = customerId;
        this.name = name;
        this.isBlackCustomer = isBlackCustomer;
    }

    public Customer toCustomer() {
        return Customer.createWithIdAndNameAndIsBlackCustomer(customerId, name, isBlackCustomer);
    }
}
