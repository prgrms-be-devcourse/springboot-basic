package com.programmers.voucher.view;

import com.programmers.voucher.view.command.Command;
import com.programmers.voucher.view.command.CustomerCommand;
import com.programmers.voucher.view.command.VoucherCommand;

import java.util.UUID;

public interface Input {
    Command readCommand();

    VoucherCommand readVoucherCommand();

    CustomerCommand readCustomerCommand();

    String readVoucherType();

    int readDiscountAmount();

    String readNickname();

    UUID readUUID();
}
