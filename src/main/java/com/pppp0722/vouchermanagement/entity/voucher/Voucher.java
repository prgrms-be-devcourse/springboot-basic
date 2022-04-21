package com.pppp0722.vouchermanagement.entity.voucher;

import com.pppp0722.vouchermanagement.entity.voucher.VoucherType;
import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherType getType();

    long getAmount();

    UUID getMemberId();
}
