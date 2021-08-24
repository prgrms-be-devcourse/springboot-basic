package org.prgrms.orderApp.domain.customer.model;

import java.util.UUID;

// Entity
public class Customer implements CustomerModel{
    private UUID customerId;
    private String customerName, customerAddress, customerContact;
}
