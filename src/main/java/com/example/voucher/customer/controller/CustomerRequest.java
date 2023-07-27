package com.example.voucher.customer.controller;

import java.util.UUID;
import com.example.voucher.constant.CustomerType;

public class CustomerRequest {

    private UUID customerId;
    private String name;
    private String email;
    private CustomerType customerType;

    private CustomerRequest(UUID customerId, String name, String email, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerType = customerType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID customerId;
        private String name;
        private String email;
        private CustomerType customerType;

        public Builder setCustomerId(UUID customerId) {
            this.customerId = customerId;

            return this;
        }

        public Builder setName(String name) {
            this.name = name;

            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;

            return this;
        }

        public Builder setCustomerType(CustomerType customerType) {
            this.customerType = customerType;

            return this;
        }

        public CustomerRequest build() {
            return new CustomerRequest(customerId, name, email, customerType);
        }

    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

}
