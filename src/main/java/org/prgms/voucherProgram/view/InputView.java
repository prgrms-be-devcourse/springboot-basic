package org.prgms.voucherProgram.view;

import org.prgms.voucherProgram.domain.voucher.VoucherType;

public interface InputView {

    String inputConsoleMenu();

    String inputVoucherMenu();

    String inputVoucherCommand();

    long inputDiscountValue(VoucherType voucherType);
}

