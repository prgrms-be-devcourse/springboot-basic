package com.prgms.management.command.io;

import com.prgms.management.voucher.model.Voucher;

public interface Input {
    String getCommand();

    Voucher getVoucher();
}
