package org.prgrms.vouchermanager.domain.wallet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.prgrms.vouchermanager.domain.voucher.Voucher;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ToString
public class Wallet {
    private final int walletId;
    private final String customerEmail;
    private final UUID voucherId;
}
