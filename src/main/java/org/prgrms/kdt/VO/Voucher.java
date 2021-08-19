package org.prgrms.kdt.VO;

public interface Voucher {
    long discount(Long beforeDiscount);

    String toString();
}
