package org.prgrms.voucher.dto;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;

public class VoucherDto {

    public record VoucherRequest(
            long discountValue,
            VoucherType voucherType
            ) {

    }

    public record VoucherResponse(
            Long voucherId,
            long discountValue,
            VoucherType voucherType
            ) {

        public static VoucherResponse from(Voucher voucher) {

            return new VoucherResponse(
                    voucher.getVoucherId(),
                    voucher.getDiscountValue(),
                    voucher.getVoucherType()
            );
        }
    }
}
