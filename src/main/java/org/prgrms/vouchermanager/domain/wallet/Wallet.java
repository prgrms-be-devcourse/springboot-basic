package org.prgrms.vouchermanager.domain.wallet;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.voucher.Voucher;

import java.util.UUID;

@RequiredArgsConstructor
public class Wallet {
    private final UUID walletId;
    private String customerEmail;
    private Voucher voucher;
}
