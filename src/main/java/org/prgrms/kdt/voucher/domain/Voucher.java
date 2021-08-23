package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

// discount라는 행위가 있다.
public interface Voucher {

    // 바우처 아이디
    UUID getVoucherId();

    Long getDiscount();

    VoucherType getVoucherType();

    // 돈을 깎아주는 행위
    Long discount(long beforeDiscount);


}
