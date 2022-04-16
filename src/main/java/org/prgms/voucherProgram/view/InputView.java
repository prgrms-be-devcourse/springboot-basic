package org.prgms.voucherProgram.view;

import org.prgms.voucherProgram.domain.voucher.VoucherType;
import org.prgms.voucherProgram.dto.CustomerDto;

public interface InputView {

    String inputConsoleMenu();

    String inputVoucherMenu();

    String inputCustomerMenu();

    String inputVoucherCommand();

    CustomerDto inputCustomerInformation();

    String inputCustomerName();

    String inputCustomerEmail();

    long inputDiscountValue(VoucherType voucherType);
}

