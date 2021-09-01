package programmers.org.kdt.engine.voucher.type;

import java.util.UUID;

public class Voucher {
    private UUID voucherId;
    private long value;
    private VoucherStatus voucherstatus;

    public Voucher(UUID voucherId, Integer value, VoucherStatus voucherstatus) {
        this.voucherId = voucherId;
        this.value = value;
        this.voucherstatus = voucherstatus;
    }

    public Voucher() {
    }

    public boolean conditionCheck() {
        return (this.voucherstatus != VoucherStatus.NULL);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherStatus getVoucherStatus() {
        return voucherstatus;
    }

    public long getValue() {
        return value;
    }

    public long discount(long beforeDiscount) {
        return 0;
    }

    public void changeValue(long i) {
        this.value = i;
    }
}
