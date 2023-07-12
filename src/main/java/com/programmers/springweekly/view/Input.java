package com.programmers.springweekly.view;

import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.dto.customer.request.CustomerCreateRequest;
import com.programmers.springweekly.dto.customer.request.CustomerUpdateRequest;
import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.request.VoucherUpdateRequest;
import java.util.UUID;

public interface Input {

    String inputMessage();

    VoucherCreateRequest inputVoucherCreate(VoucherType voucherType);

    VoucherUpdateRequest inputVoucherUpdate(UUID voucherId);

    CustomerCreateRequest inputCustomerCreate();

    CustomerUpdateRequest inputCustomerUpdate(UUID customerId);

    UUID inputUUID();
    
}
