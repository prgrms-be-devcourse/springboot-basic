package org.prgrms.voucherapp.global;

import org.prgrms.voucherapp.engine.FixedAmountVoucher;
import org.prgrms.voucherapp.engine.PercentDiscountVoucher;
import org.prgrms.voucherapp.engine.Voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

//    TODO : voucherType의 createVoucher를 voucherService에서만 접근 가능하도록 할 수 있나.
//    TODO : Yaml파일로 VoucherType enum 값들을 설정할 수는 없을까?
//    TODO : 기존 Voucher 생성자 안에서 예외처리하던 것을 Enum 내로 옮김으로써 Voucher의 종류가 늘어날때마다 같은 예외처리를 하게 되는
//     코드 중복을 없애려고 했는데 괜찮은 시도인건지?

public enum VoucherType {

    FIX("1", FixedAmountVoucher::new, 10000),
    PERCENT("2", PercentDiscountVoucher::new, 30);

    private final String option;
    private final BiFunction<UUID, Long, Voucher> createInstance;
    private final long maxDiscountAmount;

    VoucherType(String option, BiFunction<UUID, Long, Voucher> createInstance, long maxDiscountAmount) {
        this.option = option;
        this.createInstance = createInstance;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public static Voucher createVoucher(VoucherType type, UUID uuid, long amount) {
        return type.createInstance.apply(uuid, amount);
    }

    public static Optional<VoucherType> getType(String option) {
        return Arrays.stream(values())
                .filter(o -> o.option.equals(option))
                .findFirst();
    }

    public String getOption() {
        return option;
    }

    public long getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
}
