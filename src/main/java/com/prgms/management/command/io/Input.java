package com.prgms.management.command.io;

import com.prgms.management.command.exception.CommandLineException;
import com.prgms.management.voucher.entity.Voucher;

public interface Input {
    CommandType getCommandType();

    Voucher getVoucher() throws CommandLineException;
}
