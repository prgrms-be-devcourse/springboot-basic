package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.voucher.VoucherType;

public interface Input {
    Integer inputManagementCommand();

    Integer inputCustomerCommand();

    String inputCustomerStatus();

    String inputUUID();

    Integer inputVoucherCommand();

    String inputVoucherType();

    Long inputDiscountAmount(VoucherType voucherType);
}
