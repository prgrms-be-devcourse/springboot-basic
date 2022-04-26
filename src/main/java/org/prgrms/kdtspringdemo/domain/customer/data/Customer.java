package org.prgrms.kdtspringdemo.domain.customer.data;

import lombok.Getter;
import org.prgrms.kdtspringdemo.domain.customer.type.CustomerType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Customer {
    private final UUID customerId;
    private CustomerType customerType;
    private final String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    // customer 등록 시 사용 생성자
    public Customer(UUID customerId, String name, String email, LocalDateTime createdAt) {
        validateName(name);
        customerType = CustomerType.NORMAL;
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    // customer update 시 사용 생성자
    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    private void validateName(String name) {
        if(name.isBlank()){
            throw new RuntimeException("Name should not be blank");
        }
    }

    public void changeTypeToBlack(){
        this.customerType = CustomerType.BLACK;
    }

    public void changTypeToNormal(){
        this.customerType = CustomerType.NORMAL;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("customerId=").append(customerId);
        sb.append(", customerType=").append(customerType);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", lastLoginAt=").append(lastLoginAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
