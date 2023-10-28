package org.programmers.springboot.basic.domain.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Customer {

    private final UUID customerId;
    private final String name;
    private final String email;
    private CustomerType customerType;

    public int getCustomerTypeValue() {
        return this.customerType.getValue();
    }

    public void setBlack() {
        this.customerType = CustomerType.BLACK;
    }

    public void setNormal() {
        this.customerType = CustomerType.NORMAL;
    }
}
