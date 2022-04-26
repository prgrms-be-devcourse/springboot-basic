package org.prgrms.springbasic.controller.view.dto;

import lombok.Getter;
import lombok.Setter;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.domain.customer.CustomerType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CustomerDto {

    private UUID customerId;

    private CustomerType customerType;

    private String name;

    public CustomerDto() {}

    public CustomerDto(UUID customerId, CustomerType customerType, String name) {
        this.customerId = customerId;
        this.customerType = customerType;
        this.name = name;
    }

    public static CustomerDto updateCustomerDtoFrom(Customer customer) {
        return new CustomerDto(customer.getCustomerId(), customer.getCustomerType(), customer.getName());
    }
}
