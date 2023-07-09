package org.promgrammers.springbootbasic.domain.customer.dto.response;

import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;

import java.util.UUID;

public record CustomerResponse(UUID customerId, String username, CustomerType customerType) {

    public CustomerResponse(Customer customer) {
        this(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType());
    }

    public String customerOutput() {
        return "    고객 정보 [ " +
                "customerId : " + customerId +
                ", username : " + username +
                ", customerType : " + customerType +
                " ]";
    }
}
