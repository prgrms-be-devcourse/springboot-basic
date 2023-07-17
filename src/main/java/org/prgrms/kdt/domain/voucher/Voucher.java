package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.utils.VoucherType;

public interface Voucher {

    Long getVoucherId();

    VoucherType getVoucherType();

    Long getDiscountAmount();

    boolean getStatus();
}
