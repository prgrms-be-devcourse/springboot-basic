package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exceptions.AmountException;

import java.util.Optional;

public class PercentDiscountVoucher implements Voucher {

    private static final String voucherType = "percent";
    private static final int MAX_PERCENT_LIMIT = 100;
    private static final int MIN_PERCENT_LIMIT = 1;

    private final Integer percent;
    private final String voucherId;


    private String customerId;

    public PercentDiscountVoucher(String voucherId, Integer percent) {
        validateAmount(percent);
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public PercentDiscountVoucher(String voucherId, Integer percent, String customerId) {
        this(voucherId, percent);
        this.customerId = customerId;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }

    @Override
    public Optional<String> getOwnerId() {
        return Optional.ofNullable(customerId);
    }

    @Override
    public Integer discount(int beforeDiscount) {
        Double afterDiscount = beforeDiscount * (1 - percent / 100.0);
        return afterDiscount.intValue();
    }

    @Override
    public String getVoucherId() {
        return voucherId;
    }

    @Override
    public Integer getAmount() {
        return percent;
    }

    private void validateAmount(int input) {
        if (input > MAX_PERCENT_LIMIT) {
            throw new AmountException("퍼센트는 100 이하여야 합니다.");
        }
        if (input <= MIN_PERCENT_LIMIT) {
            throw new AmountException("퍼센트는 1 이상이어야 합니다.");
        }
    }
}

