package org.prgrms.kdt.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long discount(long beforeDiscount);

    public String showInfo();  // 출력을 위해서 추가
}
