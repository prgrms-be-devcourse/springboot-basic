package team.marco.vouchermanagementsystem.model;

import static java.text.MessageFormat.format;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {
    private static final int MAXIMUM_PERCENT = 100;
    private static final int MINIMUM_PERCENT = 1;

    private final int percent;

    public PercentDiscountVoucher(int percent) {
        validate(percent);

        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID id, int percent) {
        super(id);
        this.percent = percent;
    }

    private void validate(int percent) {
        if (percent < MINIMUM_PERCENT) {
            throw new IllegalArgumentException(
                    format("{0}: 할인율은 {1}% 보다 작을 수 없습니다.", percent, MINIMUM_PERCENT)
            );
        }

        if (percent > MAXIMUM_PERCENT) {
            throw new IllegalArgumentException(
                    format("{0}: 할인율은 {1}%를 초과할 수 없습니다.", percent, MAXIMUM_PERCENT)
            );
        }
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT;
    }

    @Override
    public int getData() {
        return percent;
    }

    @Override
    public String getInfo() {
        return format("{0}% 할인 쿠폰", percent);
    }
}
