package W3D2.jcu.voucher;

import java.util.UUID;

public interface Voucher {

    UUID getVoucherId();
    long discount(long beforeDiscount);
}
