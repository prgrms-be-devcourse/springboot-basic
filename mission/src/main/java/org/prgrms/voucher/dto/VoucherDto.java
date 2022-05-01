package org.prgrms.voucher.dto;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class VoucherDto {

    public record VoucherRequest(
            @Min(0)
            long discountValue,
            @NotNull
            VoucherType voucherType
            ) {

        public Voucher toDomain() {

            return this.voucherType.createVoucher(this.discountValue, this.voucherType);
        }
    }

    public record VoucherResponse(
            Long voucherId,
            long discountValue,
            VoucherType voucherType,
            LocalDateTime createdAt
            ) {

        public static VoucherResponse from(Voucher voucher) {

            return new VoucherResponse(
                    voucher.getVoucherId(),
                    voucher.getDiscountValue(),
                    voucher.getVoucherType(),
                    voucher.getCreatedAt()
            );
        }
    }
}
