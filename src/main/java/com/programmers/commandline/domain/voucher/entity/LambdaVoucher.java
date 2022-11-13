package com.programmers.commandline.domain.voucher.entity;

import java.util.UUID;

@FunctionalInterface
public interface LambdaVoucher {
    Voucher create(UUID uuid, Long discount);
}
