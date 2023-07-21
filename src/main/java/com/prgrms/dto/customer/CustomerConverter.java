package com.prgrms.dto.customer;

import com.prgrms.model.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerConverter {

    private CustomerConverter() { }

    public List<CustomerResponse> convertCustomerResponse(List<Customer> customers) {
        return customers
                .stream()
                .map(CustomerResponse::new)
                .toList();
    }

}
