package org.devcourse.voucher.domain.voucher;

public interface Voucher {
    int checkBalance(Money money);

    long getId();

}
