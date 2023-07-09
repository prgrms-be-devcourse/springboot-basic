package org.promgrammers.springbootbasic.domain.customer.model;

import lombok.Getter;
import org.promgrammers.springbootbasic.domain.Wallet;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
public final class Customer {

    private final UUID customerId;
    private String username;
    private CustomerType customerType;
    private Wallet wallet;

    public Customer(UUID customerId, String username) {
        validateUsername(username);
        this.customerId = customerId;
        this.username = username;
        this.customerType = CustomerType.WHITE;
        this.wallet = new Wallet();
    }

    private void validateUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("잘못된 이름을 입력하셨습니다. => " + username);
        }
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "Customer[" +
                "customerId=" + customerId + ", " +
                "customerType=" + customerType +
                "Wallet = " + wallet.toString() + ']';
    }
}