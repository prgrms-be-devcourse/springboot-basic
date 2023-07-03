package me.kimihiqq.vouchermanagement.domain.customer.dto;

import me.kimihiqq.vouchermanagement.domain.customer.Customer;
import me.kimihiqq.vouchermanagement.option.CustomerStatus;

import java.util.UUID;

public record CustomerDto (String name, String email, CustomerStatus customerStatus) {
    public Customer toCustomer() {
        UUID id = UUID.randomUUID();
        return new Customer(id, this.name(), this.email(), this.customerStatus());
    }
}