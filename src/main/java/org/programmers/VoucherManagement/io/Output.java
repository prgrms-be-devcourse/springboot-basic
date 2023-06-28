package org.programmers.VoucherManagement.io;

import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;

import java.util.List;

public interface Output {
    void printType();

    void printDiscountType();

    void printExitMessage();

    void printVoucherList(List<GetVoucherResponse> voucherList);

    void printInputFixedAmountMessage();

    void printInputPercentAmountMessage();
}
