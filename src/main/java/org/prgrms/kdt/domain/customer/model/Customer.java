package org.prgrms.kdt.domain.customer.model;

import org.prgrms.kdt.domain.base.BaseEntity;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer extends BaseEntity {
    private final UUID customerId;
    private String name;
    private final String email;
    private CustomerType customerType;
    private static final String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static class Builder {
        private final UUID customerId;
        private final String email;
        private String name;
        private CustomerType customerType = CustomerType.NORMAL;

        public Builder(UUID customerId, String email) {
            this.customerId = customerId;
            this.email = email;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder customerType(CustomerType customerType) {
            this.customerType = customerType;
            return this;
        }

        public Customer build() {
            return new Customer(this);
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

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    private Customer(Builder builder) {
        validateEmail(builder.email);
        customerId = builder.customerId;
        name = builder.name;
        email = builder.email;
        customerType = builder.customerType;
    }

    private void validateEmail(String email) {
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("이메일 주소를 확인해주세요");
        }
    }
}
