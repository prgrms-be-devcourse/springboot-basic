package org.prgrms.voucher.dto;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;

import java.util.concurrent.atomic.AtomicLong;

public class VoucherDto {

    public record CreateVoucherRequest(
            long discountValue,
            VoucherType voucherType) {
    }

    public record CreateVoucherResponse(
            AtomicLong voucherId,
            long discountValue,
            VoucherType voucherType) {

        public static CreateVoucherResponse of(Voucher voucher) {
            return new CreateVoucherResponse(voucher.getVoucherId(),
                    voucher.getDiscountValue(),
                    voucher.getVoucherType());
        }
    }
}
