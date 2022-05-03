package org.prgrms.springbasic.domain.customer;

import java.util.UUID;

import static org.prgrms.springbasic.domain.customer.Customer.blackCustomer;
import static org.prgrms.springbasic.domain.customer.Customer.normalCustomer;

public enum CustomerType {

    BLACK {
        @Override
        public Customer createCustomer(UUID customerId, String name) {
            return blackCustomer(customerId, name);
        }
    },
    NORMAL {
        @Override
        public Customer createCustomer(UUID customerId, String name) {
            return normalCustomer(customerId, name);
        }
    };



    public abstract Customer createCustomer(UUID customerId, String name);
}
