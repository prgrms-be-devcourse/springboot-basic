package org.prgrms.kdt.voucher;

public class DiscountValidation {
    long amount;
    float percent;

    public DiscountValidation(final long amount) {
        this.amount = amount;
    }

    public DiscountValidation(final float percent) {
        this.percent = percent;
    }

    public boolean amountValidation() {
        if (amount > 1000000 || amount <= 0) {
            System.out.println("할인 가격은 0원 초과, 1,000,000원 이하로 설정해주십시오.");
            return true;
        } else
            return false;
    }

    public boolean percentValidation() {
        if (percent > 100 || percent <= 0) {
            System.out.println("할인율은 0% 초과, 100% 이하로 설정해주십시오.");
            return true;
        } else
            return false;
    }
}
