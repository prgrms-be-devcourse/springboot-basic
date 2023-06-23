package org.promgrammers.springbootbasic.dto.response;

import java.util.List;

public record CustomersResponse(List<CustomerResponse> customers) {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("블랙 리스트 고객 목록 : \n");
        for (CustomerResponse customer : customers) {
            sb.append("  ").append(customer.toString()).append("\n");
        }
        return sb.toString();
    }
}