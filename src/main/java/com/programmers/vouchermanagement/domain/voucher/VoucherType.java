package com.programmers.vouchermanagement.domain.voucher;

import com.programmers.vouchermanagement.message.VoucherMessage;

public enum VoucherType {
    FIXED(VoucherMessage.FIXED_AMOUNT_VOUCHER_GUIDE_MESSAGE.getMessage()),
    PERCENTAGE(VoucherMessage.PERCENT_DISCOUNT_VOUCHER_GUIDE_MESSAGE.getMessage());

    private final String guideMessage;

    VoucherType(String guideMessage) {
        this.guideMessage = guideMessage;
    }

    public String getGuideMessage() {
        return guideMessage;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
