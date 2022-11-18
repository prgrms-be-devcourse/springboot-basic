package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;

import java.util.Optional;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_LIMIT = 10000;
    private static final long MIN_VOUCHER_LIMIT = 10;
    private final String voucherId;
    private final int amount;
    private static final String voucherType = "fixed";

    private String customerId;

    public FixedAmountVoucher(String voucherId, int amount) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
    }

    public FixedAmountVoucher(String voucherId, int amount, String customerId) {
        validateAmount(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.customerId = customerId;
    }
    @Override
    public String getVoucherId() {
        return voucherId;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int discount(int beforeDiscount) {
        int discountedAmount = beforeDiscount - amount;
        return Integer.max(discountedAmount, 0);
    }

    public String getVoucherType(){
        return voucherType;
    }

    @Override
    public Optional<String> getOwnerId() {
        if(customerId != null) {
            return Optional.of(customerId);
        }
        return Optional.empty();
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
