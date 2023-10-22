package team.marco.vouchermanagementsystem.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static team.marco.vouchermanagementsystem.model.voucher.VoucherType.*;

public class PercentDiscountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    private final VoucherType type = PERCENT;
    private final int percent;

    public PercentDiscountVoucher(int percent) {
        validate(percent);

        this.percent = percent;

        logger.info("Create PercentDiscountVoucher {id: {}, percent: {}}", getId(), percent);
    }

    public PercentDiscountVoucher(UUID id, int percent) {
       super(id);

        this.percent = percent;
    }

    private void validate(int percent) {
        if (percent <= 0) {
            throw new IllegalArgumentException(format("{0}: 할인율은 0% 이하일 수 없습니다.", percent));
        }

        if (percent > 100) {
            throw new IllegalArgumentException(format("{0}: 할인율은 100%를 초과할 수 없습니다.", percent));
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
        return MessageFormat.format("PercentDiscountVoucher'{'type={0}, percent={1}'}'", type, percent);
    }
}
