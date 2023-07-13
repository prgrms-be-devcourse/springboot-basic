package com.prgrms.springbootbasic.dto.customer.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerListResponse {

    private final List<CustomerResponse> customerList;
}
