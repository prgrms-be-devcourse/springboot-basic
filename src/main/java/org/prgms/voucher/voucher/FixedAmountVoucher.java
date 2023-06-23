package org.prgms.voucher.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class FixedAmountVoucher implements Voucher {

    private final long amount;
    private UUID id;

    @Override
    public long discount(long price) {
        return Math.max(0, price - amount);
    }
}
