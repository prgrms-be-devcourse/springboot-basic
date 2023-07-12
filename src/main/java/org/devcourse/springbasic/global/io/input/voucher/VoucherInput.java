package org.devcourse.springbasic.global.io.input.voucher;

import org.devcourse.springbasic.domain.voucher.domain.VoucherMenuType;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;

public interface VoucherInput {
    VoucherMenuType menu();
    VoucherType voucherMenu();
}
