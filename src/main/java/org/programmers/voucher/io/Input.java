package org.programmers.voucher.io;

import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.util.Command;
import org.programmers.voucher.util.IllegalCommandException;
import org.programmers.voucher.util.IllegalVoucherTypeException;

public interface Input {
     Command inputCommand() throws IllegalCommandException;
     VoucherType inputVoucherType() throws IllegalVoucherTypeException;
}
