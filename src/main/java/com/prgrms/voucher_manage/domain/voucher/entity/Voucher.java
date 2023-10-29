package com.prgrms.voucher_manage.domain.voucher.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class Voucher {
    private final UUID id;
    private final Long discountAmount;
    private final VoucherType type;
}
