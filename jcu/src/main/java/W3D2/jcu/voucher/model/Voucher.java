package W3D2.jcu.voucher.model;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Long discount(Long beforeDiscount);
    StringBuilder getInfo();
}
