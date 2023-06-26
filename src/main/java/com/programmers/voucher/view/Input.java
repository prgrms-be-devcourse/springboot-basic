package com.programmers.voucher.view;

import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherType;

public interface Input {
    Command readCommand();

    VoucherType readVoucherType();

    DiscountAmount readDiscountAmount(VoucherType voucherType);
}
