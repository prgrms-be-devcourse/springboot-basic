package org.prgms.kdtspringvoucher.customer.dto;

import org.prgms.kdtspringvoucher.customer.domain.CustomerType;

public class CreateCustomerRequest {
    private String name;
    private String email;
    private CustomerType customerType;

    public CreateCustomerRequest(String name, String email, CustomerType customerType) {
        this.name = name;
        this.email = email;
        this.customerType = customerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
}
