package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;

public interface ConsoleInput {
    ConsoleCommandType inputInitialCommand();

    VoucherCreateRequest inputVoucherCreateInfo();

    CustomerCreateRequest inputCustomerCreateInfo();
}
