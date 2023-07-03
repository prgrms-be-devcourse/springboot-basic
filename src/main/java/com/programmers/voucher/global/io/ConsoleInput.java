package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;

import java.util.UUID;

public interface ConsoleInput {
    ConsoleCommandType inputInitialCommand();

    VoucherCreateRequest inputVoucherCreateInfo();

    CustomerCreateRequest inputCustomerCreateInfo();

    CustomerUpdateRequest inputCustomerUpdateInfo();

    UUID inputUUID();
}
