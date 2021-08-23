package org.prgrms.kdtspringhomework.voucher.domain;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();//voucherId를 가져오는 행위

    public long discount(long beforeDiscount);

}
