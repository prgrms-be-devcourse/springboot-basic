package com.prgrms.w3springboot.io;

import com.prgrms.w3springboot.voucher.Voucher;

import java.util.List;

public interface Output {
    void printInit();

    void printTypeChoice();

    void printDiscountAmountChoice();

	void printCreatedVoucher(Voucher createdVoucher);

    void printVoucherList(List<Voucher> voucherList);

    void printExit();

    void printInvalidMessage();
}
