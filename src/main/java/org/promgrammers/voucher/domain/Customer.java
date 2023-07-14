package org.promgrammers.voucher.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@ToString
public class Customer {
    private final UUID id;
    private String username;
    private CustomerType customerType;

    public Customer(UUID id, String username) {
        validateUsername(username);
        this.id = id;
        this.username = username;
        this.customerType = CustomerType.WHITE;
    }

    private void validateUsername(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("잘못된 이름을 입력하셨습니다." + username);
        }
    }

    public void updateUsername(String username) {
        this.username = username;
    }

    public void updateCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
}
