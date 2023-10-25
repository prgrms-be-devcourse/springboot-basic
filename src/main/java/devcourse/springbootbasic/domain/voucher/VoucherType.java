package devcourse.springbootbasic.domain.voucher;

import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public enum VoucherType {
    FIXED((amount, discountValue) -> amount - discountValue) {
        @Override
        public boolean validateDiscountValue(long discountValue) {
            return discountValue > 0;
        }
    },
    PERCENT((amount, discountValue) -> amount - (amount * discountValue / 100)) {
        @Override
        public boolean validateDiscountValue(long discountValue) {
            return discountValue > 0 && discountValue <= 100;
        }
    };

    private final BiFunction<Long, Long, Long> discountFunction;

    public abstract boolean validateDiscountValue(long discountValue);

    public long applyDiscount(long amount, long discount) {
        return discountFunction.apply(amount, discount);
    }
}
