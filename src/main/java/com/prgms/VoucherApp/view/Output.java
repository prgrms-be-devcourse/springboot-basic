package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherDto;

import java.util.List;

public interface Output {
    void printDisplayMenu();

    void printDisplayVoucherPolicy();

    void printErrorMsg(String exception);

    void printDisplayDiscountCondition(VoucherType policy);

    void printCreatedMsg(Voucher voucher);

    void printVoucherList(List<VoucherDto> voucher);

    void printBlackLists(List<CustomerDto> blackLists);
}
