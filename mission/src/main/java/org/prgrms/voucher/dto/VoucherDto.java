package org.prgrms.voucher.dto;

import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;

public class VoucherDto {

    public record CreateVoucherRequest(
            long discountValue,
            VoucherType voucherType) {

    }

    public record CreateVoucherResponse(
            Long voucherId,
            long discountValue,
            VoucherType voucherType) {

    }
}
