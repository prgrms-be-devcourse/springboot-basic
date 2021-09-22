package W3D2.jcu.voucher.model;

import W3D2.jcu.Utils;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum VoucherStatus {
    // Todo : enum으로 else는 어떻게 ??
    FIXED("FIXED", FixedAmountVoucher::new),
    PERCENT("PERCENT", PercentDiscountVoucher::new);

    private final String voucherType;
    private final BiFunction<UUID,Long,Voucher> func;

    public static VoucherStatus from(String value){
        return VoucherStatus.valueOf(value.toUpperCase());
    }

    public Voucher execute(UUID id, Long amount) {
        return func.apply(id, amount);
    }

}
