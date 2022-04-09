package com.waterfogsw.voucher.console;

import com.waterfogsw.voucher.voucher.VoucherType;

public interface Input {
    Menu inputMenu();
    VoucherType inputVoucherType();
    Double inputPercent();
    Double inputAmount();
}
