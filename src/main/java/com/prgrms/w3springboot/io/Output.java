package com.prgrms.w3springboot.io;

import com.prgrms.w3springboot.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface Output {
    void printInit();

    void printTypeChoice();

    void printDiscountAmountChoice();

    void printVoucher(UUID voucherUuid);

    void printVoucherList(List<Voucher> voucherList);

    void printExit();

    void printInvalidMessage();
}
