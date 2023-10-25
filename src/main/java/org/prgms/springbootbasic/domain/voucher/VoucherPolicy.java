package org.prgms.springbootbasic.domain.voucher;

import java.util.UUID;

public interface VoucherPolicy {
    UUID getVoucherId(); // VoucherPolicy를 file로 쓸 때 id와 discount 정도를 알아야 함.
    long discount(long beforeDiscount);
    long getDiscountAmount(); // VoucherPolicy를 file로 쓸 때 id와 discount 정도를 알아야 함.
}
