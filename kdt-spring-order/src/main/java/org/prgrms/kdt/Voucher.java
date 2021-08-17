package org.prgrms.kdt;

import java.util.UUID;

//discount를 하는 행위의 약한 결합도를 위한 인터페이스
public interface Voucher {

    UUID getVoucherId();
    long discount(long beforeDiscount);

}
