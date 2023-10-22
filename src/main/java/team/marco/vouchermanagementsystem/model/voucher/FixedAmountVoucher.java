package team.marco.vouchermanagementsystem.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static team.marco.vouchermanagementsystem.model.voucher.VoucherType.*;

public class FixedAmountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);
    private static final int MIN_AMOUNT = 100;
    private static final int MAX_AMOUNT = 100000;

    private final VoucherType type = FIXED;
    private final int amount;

    public FixedAmountVoucher(int amount) {
        validate(amount);

        this.amount = amount;

        logger.debug("Create FixedAmountVoucher {id: {}, amount: {}}", getId(), amount);
    }

    public FixedAmountVoucher(UUID id, int amount) {
        super(id);

        this.amount = amount;
    }

    private void validate(int percent) {
        if (percent < MIN_AMOUNT) {
            throw new IllegalArgumentException(format("{0}: 할인 금액은 {1}원 보다 작을 수 없습니다.", amount, MIN_AMOUNT));
        }

        if (percent > MAX_AMOUNT) {
            throw new IllegalArgumentException(format("{0}: 할인 금액은 {1}원 보다 클 수 없습니다.", amount, MAX_AMOUNT));
        }
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    @Override
    public String toString() {
        return MessageFormat.format("FixedAmountVoucher'{'type={0}, amount={1}'}'", type, amount);
    }
}
