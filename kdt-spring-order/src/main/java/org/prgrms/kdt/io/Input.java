package org.prgrms.kdt.io;

import org.prgrms.kdt.voucher.domain.VoucherType;

public interface Input {
    String inputCommand();
    String inputVoucherType();
    Long inputVoucherValue();
}
