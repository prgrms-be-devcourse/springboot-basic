package programmers.org.kdt.engine.voucher.type;

import java.util.UUID;

public class Voucher {
    private UUID voucherId;
    private long value;
    private VoucherStatus voucherStatus;

    public Voucher(UUID voucherId, Integer value, VoucherStatus voucherStatus) {
        this.voucherId = voucherId;
        this.value = value;
        this.voucherStatus = voucherStatus;
    }

    public Voucher() {
    }

    public boolean conditionCheck() {
        return (this.voucherStatus != VoucherStatus.NULL);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherStatus getVoucherStatus() {
        return voucherStatus;
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
