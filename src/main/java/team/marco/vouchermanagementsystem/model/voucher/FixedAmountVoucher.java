package team.marco.vouchermanagementsystem.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static team.marco.vouchermanagementsystem.model.voucher.VoucherType.*;

public class FixedAmountVoucher extends Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private final VoucherType type = FIXED;
    private final int amount;

    public FixedAmountVoucher(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(format("{0}: 할인 금액은 0 보다 커야 합니다.", amount));
        }

        this.amount = amount;

        logger.debug("Create FixedAmountVoucher {id: {}, amount: {}}", getId(), amount);
    }

    public FixedAmountVoucher(UUID id, int amount) {
        super(id);

        this.amount = amount;
    }

    @Override
    public VoucherType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return MessageFormat.format("FixedAmountVoucher'{'type={0}, amount={1}'}'", type, amount);
    }
}
