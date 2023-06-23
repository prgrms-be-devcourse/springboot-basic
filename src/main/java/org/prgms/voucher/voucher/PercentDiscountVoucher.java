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
public class PercentDiscountVoucher implements Voucher {

    private final long percentage;
    private UUID id;

    @Override
    public long discount(long price) {
        return price * (100 - percentage) / 100;
    }

}
