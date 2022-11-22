package org.prgrms.kdt.voucher.domain;

public interface Voucher {

    long getVoucherId();

    long getDiscountDegree();

    String getTypeName();

    void validateVoucher(long discountDegree);

    Voucher changeDiscountDegree(long discountDegree);

}
