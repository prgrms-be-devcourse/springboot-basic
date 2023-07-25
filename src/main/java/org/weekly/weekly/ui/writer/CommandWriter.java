package org.weekly.weekly.ui.writer;

import org.weekly.weekly.voucher.domain.DiscountType;

public interface CommandWriter {
    void printVoucherProgram();

    void printErrorMessage(String message);

    void printCreateVoucher(DiscountType discountType);

    void printSelectDiscount();
}
