package com.zerozae.voucher.domain.customer;

import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import lombok.Getter;

import java.util.UUID;


@Getter
public class Customer {

    private final UUID customerId;
    private String customerName;
    private CustomerType customerType;

    public Customer(UUID customerId, String customerName, CustomerType customerType) {
        isValidCustomerName(customerName);
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = customerType;
    }

    public void updateCustomerInfo(CustomerUpdateRequest customerRequest) {
        isValidCustomerName(customerRequest.getCustomerName());
        this.customerName = customerRequest.getCustomerName();
        this.customerType = CustomerType.of(customerRequest.getCustomerType());
    }

    private void isValidCustomerName(String customerName) {
        if(customerName == null || customerName.isBlank()) {
            throw ExceptionMessage.error("회원 이름은 필수 입력 입니다.");
        }
    }
}
