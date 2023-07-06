package com.programmers.voucher.view;

import com.programmers.voucher.entity.voucher.VoucherType;
import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.CustomerCommand;
import com.programmers.voucher.view.dto.VoucherCommand;

import java.util.UUID;

public interface Input {
    Command readCommand();

    VoucherCommand readVoucherCommand();

    CustomerCommand readCustomerCommand();

    VoucherType readVoucherType();

    int readDiscountAmount();

    String readNickname();

    UUID readUUID();
}
