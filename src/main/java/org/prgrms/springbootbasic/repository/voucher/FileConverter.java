package org.prgrms.springbootbasic.repository.voucher;

import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.dto.VoucherDTO;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;

public class FileConverter {

    private FileConverter() {
        throw new AssertionError("util class");
    }

    public static VoucherDTO mapToVoucherDTO(Voucher voucher) {
        if (voucher.isFixed()) {
            return new VoucherDTO(
                voucher.getVoucherId(),
                VoucherType.FIXED,
                ((FixedAmountVoucher) voucher).getAmount(),
                0,
                voucher.getCustomerId()
            );
        } else {
            return new VoucherDTO(
                voucher.getVoucherId(),
                VoucherType.PERCENT,
                0,
                ((PercentDiscountVoucher) voucher).getPercent(),
                voucher.getCustomerId()
            );
        }
    }

    public static Voucher DTOToVoucher(VoucherDTO voucherDTO) {
        if (voucherDTO.getVoucherType().isFixed()) {
            return new FixedAmountVoucher(voucherDTO.getVoucherId(), voucherDTO.getAmount());
        } else {
            return new PercentDiscountVoucher(voucherDTO.getVoucherId(), voucherDTO.getPercent());
        }
    }
}
