package com.prgrms.voucher_manage.domain.wallet.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
@RequiredArgsConstructor @Getter
public class Wallet {
    private final UUID customer_id;
    private final UUID voucher_id;
}
