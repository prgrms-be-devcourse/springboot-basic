package prgms.vouchermanagementapp.service;

import prgms.vouchermanagementapp.domain.FixedAmountVoucher;
import prgms.vouchermanagementapp.domain.PercentDiscountVoucher;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory() {
    }

    public static Voucher createVoucher(Amount fixedDiscountAmount) {
        UUID voucherId = UUID.randomUUID();
        LocalDateTime createdDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        return new FixedAmountVoucher(voucherId, fixedDiscountAmount, createdDateTime);
    }

    public static Voucher createVoucher(Ratio fixedDiscountRatio) {
        UUID voucherId = UUID.randomUUID();
        LocalDateTime createdDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
        return new PercentDiscountVoucher(voucherId, fixedDiscountRatio, createdDateTime);
    }
}
