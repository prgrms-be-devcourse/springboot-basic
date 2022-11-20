package org.prgms.springbootbasic.domain.customer;


import java.util.UUID;

public record BlacklistedCustomer(UUID blackListId, UUID customerId) {
    @Override
    public String toString() {
        return "BlacklistedCustomer{" +
                "customerId='" + customerId.toString() + '\'' +
                ", blackListId='" + blackListId.toString() + '\'' +
                '}';
    }
}
