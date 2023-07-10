package org.devcourse.springbasic.global.io.input;

import org.devcourse.springbasic.domain.voucher.domain.MenuType;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;

public interface Input {

    MenuType menu();
    VoucherType voucherMenu();
}
