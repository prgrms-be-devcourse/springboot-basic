package team.marco.vouchermanagementsystem.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static java.text.MessageFormat.format;

public class PercentDiscountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private final UUID id;
    private final int percent;

    public PercentDiscountVoucher(int percent) {
        checkValidation(percent);

        this.id = UUID.randomUUID();
        this.percent = percent;

        logger.info("Create PercentDiscountVoucher {id: {}, percent: {}}", id, percent);
    }

    public PercentDiscountVoucher(UUID id, int percent) {
        this.id = id;
        this.percent = percent;
    }

    private void checkValidation(int percent) {
        if (percent <= 0) {
            throw new IllegalArgumentException(format("{0}: 할인율은 0% 이하일 수 없습니다.", percent));
        }

        if (percent > 100) {
            throw new IllegalArgumentException(format("{0}: 할인율은 100%를 초과할 수 없습니다.", percent));
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
    public int getData() {
        return percent;
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT;
    }
}
