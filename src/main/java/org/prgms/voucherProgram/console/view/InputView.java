package org.prgms.voucherProgram.console.view;

import java.util.UUID;

import org.prgms.voucherProgram.domain.customer.dto.CustomerRequest;
import org.prgms.voucherProgram.domain.voucher.dto.VoucherRequest;
import org.prgms.voucherProgram.domain.wallet.dto.WalletRequest;

public interface InputView {

    String inputConsoleMenu();

    String inputVoucherMenu();

    String inputCustomerMenu();

    String inputCustomerSubMenu();

    CustomerRequest inputCustomerInformation();

    String inputCustomerEmail();

    String inputCustomerName();

    VoucherRequest inputVoucherInformation(int voucherType);

    int inputVoucherType();

    long inputDiscountValue();

    UUID inputVoucherId();

    String inputWalletMenu();

    WalletRequest inputWalletInformation();
}
