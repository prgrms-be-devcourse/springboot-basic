package com.programmers.voucher.view;

import com.programmers.voucher.entity.voucher.VoucherType;
import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.VoucherCommand;

import java.util.UUID;

public interface Input {
    Command readCommand();

    VoucherCommand readVoucherCommand();

    VoucherType readVoucherType();

    int readDiscountAmount();

    UUID readVoucherId();
}
