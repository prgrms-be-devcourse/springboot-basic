package org.programmers.applicationcontext.voucher;
import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    long getVoucherVolume();
    long discount(long beforeDiscount);
}
