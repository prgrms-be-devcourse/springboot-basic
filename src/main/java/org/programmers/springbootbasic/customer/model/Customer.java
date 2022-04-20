package org.programmers.springbootbasic.customer.model;

import java.util.UUID;

public interface Customer {
    UUID getCustomerId();

    String getCustomerName();
}
