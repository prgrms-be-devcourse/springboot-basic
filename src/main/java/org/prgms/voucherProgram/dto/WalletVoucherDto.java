package org.prgms.voucherProgram.dto;

import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.domain.voucher.VoucherType;

public class WalletVoucherDto {
    private final UUID voucherId;
    private final UUID customerId;
    private final int type;
    private final long discountValue;

    public WalletVoucherDto(UUID voucherId, UUID customerId, int type, long discountValue) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.type = type;
        this.discountValue = discountValue;
    }

    public static WalletVoucherDto from(Voucher voucher) {
        return new WalletVoucherDto(voucher.getVoucherId(), voucher.getCustomerId(), voucher.getType(),
            voucher.getDiscountValue());
    }

    @Override
    public String toString() {
        return "voucherId=%s, customerId=%s, type=%s, discountValue = %d".formatted(voucherId, customerId,
            VoucherType.findByNumber(type), discountValue);
    }
}
