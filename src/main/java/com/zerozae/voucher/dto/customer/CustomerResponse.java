package com.zerozae.voucher.dto.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CustomerResponse {

    private UUID customerId;
    private String customerName;
    private CustomerType customerType;

    public CustomerResponse(UUID customerId, String customerName, CustomerType customerType) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerType = customerType;
    }

    public static CustomerResponse toDto(Customer customer) {
        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerType()
        );
    }

    public String getInfo(){
        return """
                회원 번호  : %s
                회원 이름  : %s
                회원 유형  : %s
                
                --------------------------------------
                """.formatted(customerId, customerName, customerType);
    }
}