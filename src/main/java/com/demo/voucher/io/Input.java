package com.demo.voucher.io;

import com.demo.voucher.domain.VoucherType;

public interface Input {
    String getMenu(String requestMenuPrompt);

    String getVoucherType(String requestVoucherTypePrompt);

    String getAmount(VoucherType voucherType);
}
