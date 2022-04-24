package org.prgrms.kdt.domain.customer.dto;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.domain.customer.model.CustomerType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerCreateRequest {
    @NotNull
    private CustomerType customerType;
    @NotBlank
    private String name;
    @Email
    private String email;

    public CustomerCreateRequest(String customerType, String name, String email) {
        this.customerType = CustomerType.findCustomerType(customerType);
        this.name = name;
        this.email = email;
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
        return new Customer(UUID.randomUUID(),
                name,
                email,
                LocalDateTime.now(),
                LocalDateTime.now());
    }
}
