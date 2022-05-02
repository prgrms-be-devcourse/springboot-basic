package org.prgms.kdtspringvoucher.customer.dto;

import org.prgms.kdtspringvoucher.customer.domain.CustomerType;

public class UpdateCustomerRequest {
    private String name;
    private CustomerType customerType;

    public UpdateCustomerRequest(String name, CustomerType customerType) {
        this.name = name;
        this.customerType = customerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
}
