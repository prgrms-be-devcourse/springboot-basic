package org.prgrms.kdt.factory;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.entity.VoucherEntity;
import org.prgrms.kdt.utils.VoucherType;

import java.util.Random;

public class VoucherFactory {

    public static Voucher createVoucher(VoucherType type, long discount) {
        switch (type) {
            case FIXED -> {
                return new FixedAmountVoucher(new Random().nextLong() > 0 ?  new Random().nextLong() : (new Random().nextLong()) * (-1), discount);
            }
            case PERCENT -> {
                return new PercentDiscountVoucher(new Random().nextLong() > 0 ?  new Random().nextLong() : (new Random().nextLong()) * (-1), discount);
            }
            default -> throw new IllegalArgumentException();
        }
    }

    public Voucher toDomain(VoucherEntity voucherEntity){
        switch (voucherEntity.getVoucherEntityType()){
            case "FIXED":
                return new FixedAmountVoucher(voucherEntity.getVoucherEntityId(),voucherEntity.getEntityAmount());

            case "PERCENT":
                return new PercentDiscountVoucher(voucherEntity.getVoucherEntityId(), voucherEntity.getEntityAmount());
            default:
                throw new IllegalArgumentException("잘못된 VoucherType 입니다.");
        }
    }
}
