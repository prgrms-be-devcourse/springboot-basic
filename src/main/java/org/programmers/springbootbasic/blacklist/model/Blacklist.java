package org.programmers.springbootbasic.blacklist.model;

import java.util.UUID;

public record Blacklist(UUID customerId, String customerName) {

    @Override
    public String toString() {
        return "BlackListCustomer" +
                " customerId= " + customerId +
                " customerName= " + customerName;
    }
}
