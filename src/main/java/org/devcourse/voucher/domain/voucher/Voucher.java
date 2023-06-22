package org.devcourse.voucher.domain.voucher;

public interface Voucher {
    Money checkBalance(Money money);

    long getId();

}
