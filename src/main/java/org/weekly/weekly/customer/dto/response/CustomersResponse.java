package org.weekly.weekly.customer.dto.response;

import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.util.PrintMessageType;

import java.util.List;

public class CustomersResponse {
    List<CustomerResponse> result;

    public CustomersResponse(List<Customer> customers) {
        result = customers.stream()
                .map(CustomerResponse::of)
                .toList();
    }

    public String result() {
        if (result.isEmpty()) {
            return PrintMessageType.NO_VOUCHER_DATAS.getMessage();
        }

        StringBuilder resultBuilder = new StringBuilder();
        result.forEach(customerResponse -> resultBuilder.append(customerResponse.result()).append('\n'));
        return resultBuilder.toString();
    }

    public List<CustomerResponse> getCustomerResponses() {
        return result;
    }
}
