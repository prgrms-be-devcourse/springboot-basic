package team.marco.vouchermanagementsystem.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static team.marco.vouchermanagementsystem.model.voucher.VoucherType.PERCENT;

public class PercentDiscountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private static final int MIN_PERCENT = 1;
    private static final int MAX_PERCENT = 50;

    private final VoucherType type = PERCENT;
    private final int percent;

    public PercentDiscountVoucher(int percent) {
        validate(percent);
        this.percent = percent;

        logger.info("Create PercentDiscountVoucher {id: {}, percent: {}}", getId(), percent);
    }

    public PercentDiscountVoucher(UUID id, int percent) {
       super(id);
        validate(percent);
        this.percent = percent;

        logger.info("Create PercentDiscountVoucher {id: {}, percent: {}}", getId(), percent);
    }

    public PercentDiscountVoucher(int percent, UUID ownerId) {
        validate(percent);
        this.percent = percent;

        assigneOwner(ownerId);

        logger.info("Create PercentDiscountVoucher {id: {}, percent: {}}", getId(), percent);
    }

    public PercentDiscountVoucher(UUID id, Integer percent, UUID ownerId) {
        super(id, ownerId);
        validate(percent);
        this.percent = percent;

        logger.info("Create PercentDiscountVoucher {id: {}, percent: {}}", getId(), percent);
    }

    private void validate(int percent) {
        if (percent <= MIN_PERCENT || percent >= MAX_PERCENT) {
            throw new IllegalArgumentException(format("{0}: 할인율은 {1}% 보다 작거나 {2}% 보다 클 수 없습니다.", percent, MIN_PERCENT, MAX_PERCENT));
        }
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}% 할인 쿠폰 {1}", percent, getOwnerId() == null ? "" : "/ 쿠폰 소지자: " + getOwnerId());
    }
}
