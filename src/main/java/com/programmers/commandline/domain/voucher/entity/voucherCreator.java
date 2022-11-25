package com.programmers.commandline.domain.voucher.entity;

import java.time.LocalDateTime;
import java.util.UUID;

@FunctionalInterface
public interface voucherCreator {
    Voucher create(UUID id, Long discount, LocalDateTime createdAt);
}
