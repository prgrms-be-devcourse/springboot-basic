package org.prgrms.kdt.kdtspringorder.voucher.domain;

import java.util.UUID;

/**
 * 바우처 인터페이스
 */
public interface Voucher {

    UUID getVoucherId();

    long discount(long beforeDiscount);

}
