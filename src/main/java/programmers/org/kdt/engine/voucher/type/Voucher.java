package programmers.org.kdt.engine.voucher.type;

import java.util.UUID;
import programmers.org.kdt.engine.voucher.type.VoucherStatus;

public interface Voucher {

    boolean conditionCheck();

    UUID getVoucherId();

    VoucherStatus getVoucherStatus();

    long getValue();

    long discount(long beforeDiscount);


};
