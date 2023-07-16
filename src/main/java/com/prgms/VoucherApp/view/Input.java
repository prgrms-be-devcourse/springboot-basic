package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.voucher.model.VoucherType;

public interface Input {
    int inputManagementCommand();

    int inputCustomerCommand();

    String inputCustomerStatus();

    String inputUUID();

    int inputVoucherCommand();

    String inputVoucherType();

    long inputDiscountAmount(VoucherType voucherType);

    int inputWalletCommand();
}
