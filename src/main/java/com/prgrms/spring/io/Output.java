package com.prgrms.spring.io;

import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.exception.Success;

import java.util.List;

public interface Output {
    void showMenu();
    void showVoucherTypes();
    void showVoucherPrompt(VoucherType voucherType);
    void showAllVouchers(List<String> vouchers);
    void showErrorMsg(Error error);
    void showSuccessMsg(Success success);

    void showGetName();
    void showGetEmail();
    void showAllCustomers(List<String> customers);
}
