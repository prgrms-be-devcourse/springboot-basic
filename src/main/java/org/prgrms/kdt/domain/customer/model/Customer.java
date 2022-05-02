package org.prgrms.kdt.domain.customer.model;

import org.prgrms.kdt.domain.common.model.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

public class Customer extends BaseEntity {
    private final UUID customerId;
    private String name;
    private final String email;
    private CustomerType customerType = CustomerType.NORMAL;
    private static final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public Customer(UUID customerId, String name, String email, CustomerType customerType, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        validateEmail(email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.customerType = customerType;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        validateEmail(email);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    private void validateEmail(String email) {
        if(!emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 주소를 확인해주세요");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", customerType=" + customerType +
                ", createdDateTime=" + getCreatedDateTime().toString() +
                ", modifiedDateTime=" + getModifiedDateTime().toString() +
                '}';
    }
}
