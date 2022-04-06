package org.prgms.voucher.view;

import org.prgms.voucher.entity.VoucherType;

public interface InputView {

    String inputMenu();

    String inputVoucherCommand();

    long inputDiscountValue(VoucherType voucherType);
}
