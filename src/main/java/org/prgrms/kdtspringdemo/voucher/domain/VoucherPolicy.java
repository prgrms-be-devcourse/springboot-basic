package org.prgrms.kdtspringdemo.voucher.domain;

public interface VoucherPolicy {
    String getVoucherType();
    long getAmount();
    long discount(long beforeDiscount);
}
