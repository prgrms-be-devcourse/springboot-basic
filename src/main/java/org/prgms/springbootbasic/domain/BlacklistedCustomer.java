package org.prgms.springbootbasic.domain;


import java.util.UUID;

public class BlacklistedCustomer {
    private final UUID customerId;
    private final UUID blackListId;

    public BlacklistedCustomer(UUID blackListId, UUID customerId) {
        this.blackListId = blackListId;
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "BlacklistedCustomer{" +
                "customerId='" + customerId.toString() + '\'' +
                ", blackListId='" + blackListId.toString() + '\'' +
                '}';
    }
}
