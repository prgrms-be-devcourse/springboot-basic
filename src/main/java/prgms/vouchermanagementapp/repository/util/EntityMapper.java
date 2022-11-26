package prgms.vouchermanagementapp.repository.util;

import prgms.vouchermanagementapp.domain.*;

import java.text.MessageFormat;

public class EntityMapper {

    private EntityMapper() {
    }

    public static VoucherEntity toVoucher(Voucher voucher, Customer customer) {
        String uuid = voucher.getVoucherId().toString();
        String voucherType = voucher.getClass().getSimpleName();
        String customerName = customer.getCustomerName();

        return createVoucherEntity(voucher, uuid, voucherType, customerName);
    }

    private static VoucherEntity createVoucherEntity(Voucher voucher, String uuid, String voucherType, String customerName) {
        if (voucher instanceof FixedAmountVoucher) {
            Long amount = ((FixedAmountVoucher) voucher).getFixedDiscountLevel();
            return new VoucherEntity(uuid, voucherType, amount, 0L, customerName);
        }
        if (voucher instanceof PercentDiscountVoucher) {
            Long ratio = ((PercentDiscountVoucher) voucher).getFixedDiscountLevel();
            return new VoucherEntity(uuid, voucherType, 0L, ratio, customerName);
        }

        throw new UnsupportedOperationException(
                MessageFormat.format(
                        "voucher type '{0}' is not supported yet.",
                        voucher.getClass().getSimpleName()
                )
        );
    }
}
