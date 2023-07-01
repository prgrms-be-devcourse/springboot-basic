package org.promgrammers.springbootbasic.domain.customer.dto.response;

import java.util.List;

public record CustomersResponse(List<CustomerResponse> customers) {

    public String blackTypeOutput() {
        StringBuilder sb = new StringBuilder();
        sb.append("블랙 리스트 고객 목록 : \n");
        for (CustomerResponse customer : customers) {
            sb.append("  ").append(customer.customerOutput()).append("\n");
        }
        return sb.toString();
    }

    public String allCustomerOutput() {
        StringBuilder sb = new StringBuilder();
        sb.append("등록된 고객 목록 \n");
        for (CustomerResponse customerResponse : customers) {
            sb.append("  ").append(customerResponse.customerOutput()).append("\n");
        }
        return sb.toString();
    }

}