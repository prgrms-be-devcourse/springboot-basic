package org.prgrms.dev.voucher.domain;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId(); // 바우처 아이디를 가져오는 행위

    long discount(long beforeDiscount);
}
