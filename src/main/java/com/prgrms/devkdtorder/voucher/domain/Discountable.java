package com.prgrms.devkdtorder.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Discountable {
    long discount(long beforeDiscount);
}
