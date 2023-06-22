package kr.co.programmers.springbootbasic.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getAmount();
}
