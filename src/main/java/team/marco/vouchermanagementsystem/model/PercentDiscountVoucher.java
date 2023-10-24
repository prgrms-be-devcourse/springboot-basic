package team.marco.vouchermanagementsystem.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static java.text.MessageFormat.format;

public class PercentDiscountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final int MINIMUM_PERCENT = 1;
    private static final int MAXIMUM_PERCENT = 100;

    private final UUID id;
    private final int percent;

    public PercentDiscountVoucher(int percent) {
        validate(percent);

        this.id = UUID.randomUUID();
        this.percent = percent;

        logger.info("Create PercentDiscountVoucher {id: {}, percent: {}}", id, percent);
    }

    public PercentDiscountVoucher(UUID id, int percent) {
        this.id = id;
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
    public UUID getId() {
        return id;
    }

    @Override
    public String getInfo() {
        return format("{0}% 할인 쿠폰", percent);
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT;
    }

    public int getPercent() {
        return percent;
    }
}
