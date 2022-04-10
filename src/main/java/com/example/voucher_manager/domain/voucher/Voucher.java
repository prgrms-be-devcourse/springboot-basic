package com.example.voucher_manager.domain.voucher;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();
    Long discount(Long regularPrice);
}
