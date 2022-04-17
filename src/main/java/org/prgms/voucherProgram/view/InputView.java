package org.prgms.voucherProgram.view;

import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.dto.VoucherDto;

public interface InputView {

    String inputConsoleMenu();

    String inputVoucherMenu();

    String inputCustomerMenu();

    String inputCustomerSubMenu();

    int inputVoucherType();

    VoucherDto inputVoucherInformation(int voucherType);

    CustomerDto inputCustomerInformation();

    String inputCustomerName();

    String inputCustomerEmail();

    Long inputDiscountPercent();

    Long inputDiscountAmount();
}
