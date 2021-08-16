package org.prgrms.kdt.entity;

import java.util.UUID;

// discount라는 행위가 있다.
public interface Voucher {

    // 바우처 아이디
    UUID getVoucherId();

    // 돈을 깎아주는 행위
    long discount(long beforeDiscount);


}
