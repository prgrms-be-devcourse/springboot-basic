package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;

import java.util.Optional;

public class FixedAmountVoucher implements Voucher {
    private static final String voucherType = "fix";
    private static final long MAX_VOUCHER_LIMIT = 10000;
    private static final long MIN_VOUCHER_LIMIT = 10;

    private final String voucherId;
    private final Integer amount;

    private String customerId;


    public FixedAmountVoucher(String voucherId, Integer amount) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public FixedAmountVoucher(String voucherId, Integer amount, String customerId) {
        this(voucherId, amount);
        this.customerId = customerId;
    }
    @Override
    public String getVoucherId() {
        return voucherId;
    }

    @Override
    public Integer getAmount() {
        return amount;
    }

    @Override
    public Integer discount(int beforeDiscount) {
        int discountedAmount = beforeDiscount - amount;
        return Integer.max(discountedAmount, 0);
    }

    public String getVoucherType(){
        return voucherType;
    }

    @Override
    public Optional<String> getOwnerId() {
        return Optional.ofNullable(customerId);
    }

    private void validateAmount(int input){
        if (input <= MIN_VOUCHER_LIMIT) {
            throw new AmountException("숫자가 %d보다 커야 합니다. 다시 작성해주세요.".formatted(MIN_VOUCHER_LIMIT));
        }
        if (input > MAX_VOUCHER_LIMIT) {
            throw new AmountException("%d 이하여야 합니다.".formatted(MAX_VOUCHER_LIMIT));
        }
    }

}
