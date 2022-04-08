package org.prgrms.vouchermanager.voucher;


import java.util.UUID;

public class PercentDiscountVoucher extends AbstractVoucher {

    private final long percent;

    // UUID를 생성자로 입력받지 않고 내부에서 생성하도록 작성
    public PercentDiscountVoucher(long percent) {
        super(UUID.randomUUID(), VoucherType.PERCENT);
        this.percent = percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return 0;
    }

    @Override
    public String toString() {
        //TODO: 후에 출력 양식 정하면 StringBuilder나 Message Format으로 바꿀 것
        return "PercentDiscountVoucher{" +
                "voucherId+" + getVoucherId() +
                "voucherType" + getVoucherType() +
                "percent=" + percent +
                '}';
    }
}
