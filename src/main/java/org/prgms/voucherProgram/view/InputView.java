package org.prgms.voucherProgram.view;

import org.prgms.voucherProgram.entity.voucher.VoucherType;

public interface InputView {

    String inputMenu();

    String inputVoucherCommand();

    long inputDiscountValue(VoucherType voucherType);
}
