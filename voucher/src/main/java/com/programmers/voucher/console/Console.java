package com.programmers.voucher.console;

import com.programmers.voucher.domain.Type;
import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.domain.voucher.VoucherEnum;

public interface Console {
    Type getCondition();

    VoucherEnum getVoucherVersion();

    Voucher createFixedVoucher();

    Voucher createPercentVoucher();
}
