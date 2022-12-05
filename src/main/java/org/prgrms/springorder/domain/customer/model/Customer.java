package org.prgrms.springorder.domain.customer.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;


@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "customerId")
@ToString
public class Customer {

    private final UUID customerId;

    private String name;

    private String email;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;

    private CustomerStatus customerStatus;

    protected Customer(UUID customerId) {
        this.customerId = customerId;
    }

    public Customer(UUID customerId, String name, String email) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        customerStatus = CustomerStatus.NORMAL;
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name should not be black");
        }
    }

    public void updateLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public void block(CustomerStatus blockStatus) {
        this.customerStatus = blockStatus;
    }

}