package org.prgms.kdtspringvoucher.io;

import org.prgms.kdtspringvoucher.commandLine.CommandType;
import org.prgms.kdtspringvoucher.customer.domain.CustomerType;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.prgms.kdtspringvoucher.wallet.WalletCommandType;

public interface Input {
    CommandType inputCommandType();
    VoucherType inputVoucherType();
    WalletCommandType inputWalletCommandType();
    Long inputDiscountAmountOrPercent();
    String inputCustomerName();
    String inputCustomerEmail();
    CustomerType inputCustomerType();
    int inputCustomerNumber();
    int inputVoucherNumber();
}
