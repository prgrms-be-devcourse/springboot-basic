package com.programmers.voucher.io;

import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.request.VoucherCreateRequest;

public interface Console {
    ConsoleCommandType inputInitialCommand();

    void printCommandSet();

    VoucherCreateRequest inputVoucherCreateInfo();

    void print(String result);

    void exit();
}
