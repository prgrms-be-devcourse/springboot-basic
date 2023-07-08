package com.prgrms.springbootbasic.domain.voucher;

import com.prgrms.springbootbasic.enums.VoucherType;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    long getDiscount();

    VoucherType getVoucherType();
}
