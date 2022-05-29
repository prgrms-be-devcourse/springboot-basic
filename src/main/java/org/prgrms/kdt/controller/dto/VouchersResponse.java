package org.prgrms.kdt.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.kdt.view.VoucherMenu;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;

public class VouchersResponse {

    private UUID id;
    private long value;
    private VoucherType type;
    private LocalDateTime createdAt;

    public VouchersResponse(UUID id, long value, VoucherType type,
        LocalDateTime createdAt) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static VouchersResponse of(Voucher voucher) {
        return new VouchersResponse(
            voucher.getId(),
            voucher.getValue(),
            voucher.getVoucherType(),
            voucher.getCreatedAt()
        );
    }

    public UUID getId() {
        return id;
    }

    public long getValue() {
        return value;
    }

    public VoucherType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
