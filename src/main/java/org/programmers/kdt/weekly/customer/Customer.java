package org.programmers.kdt.weekly.customer;


import java.util.UUID;
import lombok.Getter;

@Getter
public class Customer {

    private final UUID customerId;
    private final String customerName;
    private final CustomerType customerType;

    public Customer(UUID customerId, String customerName, CustomerType customerType) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return
            "customerId= " + customerId +
                ", customerName= " + customerName +
                ", customerType= " + customerType;
    }
}
