package org.promgrammers.springbootbasic.domain.customer.dto.response;

import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;

import java.util.UUID;

public record CustomerResponse(UUID customerId, String username, CustomerType customerType) {

    @Override
    public String toString() {
        return "[" +
                "customerId : '" + customerId + '\'' +
                ", username : '" + username + '\'' +
                ", customerType : '" + customerType + '\'' +
                ']';
    }
}
