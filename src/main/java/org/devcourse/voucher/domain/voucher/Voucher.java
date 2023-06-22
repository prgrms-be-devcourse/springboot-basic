package org.devcourse.voucher.domain.voucher;

public interface Voucher {
    Money retrievePostBalance(Money money);

    long getId();

}
