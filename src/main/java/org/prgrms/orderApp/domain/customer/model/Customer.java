package org.prgrms.orderApp.domain.customer.model;

import java.util.UUID;

// Entity
public class Customer implements CustomerModel{
    private UUID customerId;
    private String customerName, customerAddress, customerContact;

    public Customer(UUID customerId){
        this.customerId = customerId;
    }
    public Customer(UUID customerId, String customerName, String customerAddress, String customerContact){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerContact = customerContact;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "customerId=" + customerId +
                ", customerName=" + customerName +
                ", customerAddress=" + customerAddress +
                ", customerContact=" + customerContact +
                "}";
    }
}
