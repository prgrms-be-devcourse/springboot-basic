package com.prgrms.custoemer.dto;

import com.prgrms.custoemer.model.Customer;
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
