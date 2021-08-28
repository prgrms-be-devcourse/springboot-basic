package org.prgrms.kdt.customer;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by yhh1056
 * Date: 2021/08/23 Time: 11:31 오후
 */
public class Customer {

    private final UUID customerId;

    private String name;

    private final String email;

    private final LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    private CustomerType customerType;

    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void changeTypeOfBlackList() {
        this.customerType = CustomerType.BLACKLIST;
    }


    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
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
