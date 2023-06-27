package programmers.org.voucher.domain;

import programmers.org.voucher.constant.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    int getDiscountAmount();
}
