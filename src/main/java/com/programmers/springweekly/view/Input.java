package com.programmers.springweekly.view;

import com.programmers.springweekly.dto.CustomerCreateDto;
import com.programmers.springweekly.dto.CustomerUpdateDto;

import java.util.UUID;

public interface Input {

    String inputMessage();

    CustomerCreateDto inputCustomerCreate();

    CustomerUpdateDto inputCustomerUpdate(UUID customerId);

    UUID inputUUID();
}
