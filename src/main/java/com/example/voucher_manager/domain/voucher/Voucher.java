package com.example.voucher_manager.domain.voucher;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher extends Serializable {
    UUID getVoucherId();
    Long discount(Long regularPrice);
}
