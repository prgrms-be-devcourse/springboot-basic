package org.devcourse.voucher.domain.voucher;

public class DiscountVoucher extends Voucher {

    private final int amount;
    private final DiscountPolicy discountPolicy;

    protected DiscountVoucher(long id, VoucherType type, int amount) {
        super(id, type);
        validateAmount(type, amount);
        this.amount = amount;
        this.discountPolicy = selectPolicy(type);
    }

    private void validateAmount(VoucherType type, int amount) {
        boolean incorrectAmountRange = switch (type) {
            case PERCENT -> amount < 0 || amount > 100;
            case FIX -> amount < 0 || amount > 100_000;
        };

        if (incorrectAmountRange) {
            throw new RuntimeException("바우처 금액 범위 오류");
        }
    }

    private DiscountPolicy selectPolicy(VoucherType type) {
        return switch (type) {
            case PERCENT -> originPrice -> originPrice.minus(originPrice.percent(amount));
            case FIX -> originPrice -> originPrice.minus(Money.of(amount));
        };
    }

    @Override
    public Money retrievePostBalance(Money targetMoney) {
        return discountPolicy.discount(targetMoney);
    }

}
