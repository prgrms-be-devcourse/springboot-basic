package org.prgms.kdt.application.voucher.controller.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.prgms.kdt.application.voucher.domain.FixedAmountVoucher;
import org.prgms.kdt.application.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;

@Getter @Setter
public class VoucherRequestDto {
    VoucherType voucherType;
    long discountValue;

    public Voucher getVoucher() {
        Voucher voucher = null;
        switch (voucherType) {
            case FIXED_AMOUNT:
                voucher = new FixedAmountVoucher(UUID.randomUUID(), null, discountValue,
                    LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                break;
            case PERCENT_DISCOUNT:
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), null, discountValue,
                    LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                break;
            default:
                break;
        }
        return voucher;
    }
}
