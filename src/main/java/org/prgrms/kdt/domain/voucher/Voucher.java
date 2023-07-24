package org.prgrms.kdt.domain.voucher;

import org.prgrms.kdt.utils.VoucherStatus;
import org.prgrms.kdt.utils.VoucherType;

import java.io.Serializable;

public interface Voucher extends Serializable {

    Long getVoucherId();

    VoucherType getVoucherType();

    Long getDiscountAmount();

    String getStatus();
}
