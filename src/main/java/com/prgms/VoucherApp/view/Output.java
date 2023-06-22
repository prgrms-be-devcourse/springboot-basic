package com.prgms.VoucherApp.view;

import com.prgms.VoucherApp.domain.Voucher;
import com.prgms.VoucherApp.domain.VoucherPolicy;

import java.util.List;

public interface Output {
    void outputDisplayMenu();

    void outputDisplayVoucherPolicy();

    void outputDisplayDiscountCondition(VoucherPolicy policy);

    void outputNotCreatedMsg();

    void outputCreatedMsg(Voucher voucher);

    void outputVoucherHistory(List<Voucher> voucher);
}
