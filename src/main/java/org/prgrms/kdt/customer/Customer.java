package org.prgrms.kdt.customer;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 11:31 오후
 */
public class Customer {

    private final UUID customerId;

    private String name;

    private CustomerType customerType;

    public Customer(UUID customerId, String name, CustomerType customerType) {
        this.customerId = customerId;
        this.name = name;
        this.customerType = customerType;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return MessageFormat
                .format("Customer'{'customerId={0}, name=''{1}'', customerType={2}'}'",
                        customerId,
                        name,
                        customerType);
    }
}
