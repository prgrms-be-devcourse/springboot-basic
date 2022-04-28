package org.prgms.management.model.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Voucher {
    void changeName(String name);

    void changeDiscountNum(int discountNum);

    UUID getVoucherId();

    String getType();

    String getName();

    int getDiscountNum();

    LocalDateTime getCreatedAt();
}
