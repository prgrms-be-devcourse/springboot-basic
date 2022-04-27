package org.prgrms.voucherprgrms.voucher.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class VoucherDTO {

    public final UUID voucherId;

    public final String voucherType;

    public final long value;

    public final LocalDateTime createdAt;

    public VoucherDTO(UUID voucherId, String voucherType, long value, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.value = value;
        this.createdAt = createdAt;
    }

    public static VoucherDTO toVoucherDTO(Voucher voucher) {
        return new VoucherDTO(voucher.getVoucherId(), voucher.getDTYPE(), voucher.getValue(), voucher.getCreatedAt());
    }
}
