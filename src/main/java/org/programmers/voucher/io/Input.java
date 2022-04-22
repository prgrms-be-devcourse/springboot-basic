package org.programmers.voucher.io;

import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.util.Command;

public interface Input {
     Command inputCommand() throws IllegalArgumentException;
     VoucherType inputVoucherType() throws IllegalArgumentException;
     long inputAmount();
     double inputPercent();
}
