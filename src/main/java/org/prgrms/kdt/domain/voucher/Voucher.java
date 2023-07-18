package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.utils.VoucherStatus;
import org.prgrms.kdt.utils.VoucherType;

public interface Voucher {

    Long getVoucherId();

    VoucherType getVoucherType();

    Long getDiscountAmount();

    String getStatus();
}
