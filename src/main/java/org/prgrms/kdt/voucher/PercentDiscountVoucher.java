package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;

import java.util.Optional;

public class PercentDiscountVoucher implements Voucher {
    private final String voucherId;
    private final int MAX_PERCENT_LIMIT = 100;
    private final int MIN_PERCENT_LIMIT = 1;
    private final int percent;

    private static final String voucherType = "percent";

    private String customerId;

    public PercentDiscountVoucher(String voucherId, int percent) {
        validateAmount(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public PercentDiscountVoucher(String voucherId, int percent, String customerId) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.customerId = customerId;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }

    @Override
    public Optional<String> getOwnerId() {
        if(customerId == null){
            return Optional.empty();
        }
        return Optional.of(customerId);
    }

    @Override
    public int discount(int beforeDiscount) {
        return beforeDiscount - (beforeDiscount * (percent / 100));
    }

    @Override
    public String getVoucherId() {
        return voucherId;
    }

    @Override
    public int getAmount() {
        return percent;
    }

    private void validateAmount(int input){
        if (input > MAX_PERCENT_LIMIT) {
            throw new AmountException("퍼센트는 100 이하여야 합니다.");
        }
        if (input <= MIN_PERCENT_LIMIT) {
            throw new AmountException("퍼센트는 1 이상이어야 합니다.");
        }
    }
}

