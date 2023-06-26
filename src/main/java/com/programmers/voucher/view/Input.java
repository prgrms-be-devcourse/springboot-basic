package com.programmers.voucher.view;

import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.DiscountAmount;
import com.programmers.voucher.view.dto.VoucherCommand;

public interface Input {
    Command readCommand();

    VoucherCommand readVoucherCommand();

    DiscountAmount readDiscountAmount(VoucherCommand voucherCommand);
}
