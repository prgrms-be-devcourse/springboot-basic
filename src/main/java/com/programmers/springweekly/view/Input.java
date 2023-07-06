package com.programmers.springweekly.view;

import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;

import java.util.UUID;

public interface Input {

    String inputMessage();

    CustomerCreateRequest inputCustomerCreate();

    CustomerUpdateRequest inputCustomerUpdate(UUID customerId);

    UUID inputUUID();
}
