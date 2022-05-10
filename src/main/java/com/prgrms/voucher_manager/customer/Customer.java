package com.prgrms.voucher_manager.customer;

import com.prgrms.voucher_manager.customer.controller.CustomerDto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public interface Customer {

    UUID getCustomerId();

    void loginInNow();

    void changeName(String name);

    CustomerDto toCustomerDto();

    Map<String, Object> toMap();

}
