package org.prgrms.voucherapp.global;

import org.prgrms.voucherapp.engine.FixedAmountVoucher;
import org.prgrms.voucherapp.engine.PercentDiscountVoucher;
import org.prgrms.voucherapp.engine.Voucher;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

// TODO : 랜덤으로 만든 voucherId가 repository에 이미 존재한다면?
public enum VoucherType {

    FIX("1", FixedAmountVoucher::new),
    PERCENT("2", PercentDiscountVoucher::new);

    private final String option;
    private final BiFunction<UUID, Long, Voucher> createInstance;
//    private final Function<Long, Boolean> checkAmount;

    VoucherType(String option, BiFunction<UUID, Long, Voucher> createInstance){
        this.option = option;
        this.createInstance = createInstance;
//        this.checkAmount = checkAmount;
    }

    public static Voucher createVoucher(VoucherType type, UUID uuid, long amount){
        return type.createInstance.apply(uuid, amount);
    }

}
