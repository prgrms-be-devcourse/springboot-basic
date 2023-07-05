package com.programmers.voucher.view;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateResponse;
import com.programmers.voucher.domain.Voucher;

public interface Output {
    void displayCommands();

    void displayVoucherCommands();

    void displayVoucherType();

    void displayCreatedVoucher(VoucherCreateResponse voucher);

    void displayVoucher(Voucher voucher);
}
