package team.marco.vouchermanagementsystem.repository.voucher;

import team.marco.vouchermanagementsystem.model.voucher.FixedAmountVoucher;
import team.marco.vouchermanagementsystem.model.voucher.PercentDiscountVoucher;
import team.marco.vouchermanagementsystem.model.voucher.Voucher;
import team.marco.vouchermanagementsystem.model.voucher.VoucherType;

import java.util.UUID;

public class LoadedJsonVoucher {
    private final UUID id;
    private final VoucherType type;
    private final Integer amount;
    private final Integer percent;
    private final UUID ownerId;

    public LoadedJsonVoucher(UUID id, VoucherType type, Integer amount, Integer percent, UUID ownerId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.percent = percent;
        this.ownerId = ownerId;
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getPercent() {
        return percent;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Voucher jsonVoucherToVoucher() {
        return switch (type) {
            case FIXED -> {
                if(ownerId == null) {
                    yield new FixedAmountVoucher(id, amount);
                } else {
                    yield new FixedAmountVoucher(id, amount, ownerId);
                }
            }
            case PERCENT -> {
                if (ownerId == null) {
                    yield new PercentDiscountVoucher(id, percent);
                } else {
                    yield new PercentDiscountVoucher(id, percent, ownerId);
                }
            }
        };
    }
}
