package org.prgrms.springbootbasic.entity;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.prgrms.springbootbasic.type.VoucherType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

import static org.prgrms.springbootbasic.type.VoucherType.FIXED;
import static org.prgrms.springbootbasic.type.VoucherType.PERCENT;


@Builder
@Getter
public class Voucher {
    private final UUID voucherId;

    @NotBlank
    private final VoucherType voucherType;

    @Min(0)
    private final long discountQuantity;

    @Range(min = 0, max = 100)
    private final long discountRatio;

    @Min(0)
    private final long voucherCount;

    @NotNull
    private final LocalDate createdAt;

    @NotNull
    private final LocalDate endedAt;

    public long discount(long regularPrice) {
        if (voucherType == FIXED) {
            return Math.max(0, regularPrice - discountQuantity);
        } else if (voucherType == PERCENT) {
            return regularPrice * (1 - discountRatio);
        }
        return regularPrice;
    }
}
