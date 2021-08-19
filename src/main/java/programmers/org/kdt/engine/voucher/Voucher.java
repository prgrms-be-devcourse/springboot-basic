package programmers.org.kdt.engine.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();

    VoucherStatus getVoucherStatus();

    long getValue();

    long discount(long beforeDiscount);

};
