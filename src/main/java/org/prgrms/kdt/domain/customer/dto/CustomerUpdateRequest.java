package org.prgrms.kdt.domain.customer.dto;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerUpdateRequest {
    @NotNull
    private UUID customerId;
    @NotNull
    private CustomerType customerType;
    @NotBlank
    private String name;
    @Email
    private String email;

    public CustomerUpdateRequest(UUID customerId, String customerType, String name, String email) {
        this.customerType = CustomerType.findCustomerType(customerType);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Customer toEntity() {
        return new Customer(customerId,
                name,
                email,
                customerType,
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
