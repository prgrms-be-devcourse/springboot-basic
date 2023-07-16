package com.programmers.springweekly.dto.customer.response;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CustomerResponse {

    private UUID customerId;
    private String customerName;
    private String customerEmail;
    private CustomerType customerType;

    public CustomerResponse(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerName = customer.getCustomerName();
        this.customerEmail = customer.getCustomerEmail();
        this.customerType = customer.getCustomerType();
    }

    @Override
    public String toString() {
        return "고객이 생성되었습니다." +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerType=" + customerType +
                '}';
    }
    
}
