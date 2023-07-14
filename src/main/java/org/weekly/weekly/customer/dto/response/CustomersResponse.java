package org.weekly.weekly.customer.dto.response;

import org.weekly.weekly.customer.domain.Customer;
import org.weekly.weekly.util.PrintMessageType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.dto.Response;
import org.weekly.weekly.voucher.dto.response.VoucherCreationResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CustomersResponse implements Response {
    List<CustomerResponse> result;

    public CustomersResponse(List<Customer> customers) {
        result = customers.stream()
                .map(CustomerResponse::of)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public String getResult() {
        if (result.isEmpty()) {
            return PrintMessageType.NO_VOUCHER_DATAS.getMessage();
        }

        StringBuilder resultBuilder = new StringBuilder();
        result.forEach(customerResponse-> resultBuilder.append(customerResponse.getResult()).append('\n'));
        return resultBuilder.toString();
    }
}
