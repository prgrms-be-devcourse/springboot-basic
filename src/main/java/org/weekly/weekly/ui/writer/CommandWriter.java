package org.weekly.weekly.ui.writer;

import org.weekly.weekly.voucher.domain.DiscountType;

public interface CommandWriter {
    void printVoucherProgram();

    void printErrorMsg(String message);

    void printCreateVoucher(DiscountType discountType);

    void printSelectDiscount();

    void printReuslt(String result);
}
