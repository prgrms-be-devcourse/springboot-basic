package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.Voucher;
import com.prgms.VoucherApp.domain.VoucherType;

import java.util.List;

public interface Output {
    void printDisplayMenu();

    void printDisplayVoucherPolicy();

    void printDisplayDiscountCondition(VoucherType policy);

    void printNotCreatedMsg();

    void printCreatedMsg(Voucher voucher);

    void printVoucherList(List<Voucher> voucher);
}
