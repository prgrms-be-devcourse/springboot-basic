package org.prgrms.kdt;

import java.util.UUID;

public interface Voucher {
    UUID getVoucher();
    long discount(long beforeDiscount);

    public String showInfo();  // 출력을 위해서 추가
}
