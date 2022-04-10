package com.prgms.management.command.io;

import com.prgms.management.command.exception.CommandLineException;
import com.prgms.management.voucher.entity.Voucher;

public interface Input {
    CommandType getCommandType() throws CommandLineException;
    Voucher getVoucher() throws CommandLineException;
}
