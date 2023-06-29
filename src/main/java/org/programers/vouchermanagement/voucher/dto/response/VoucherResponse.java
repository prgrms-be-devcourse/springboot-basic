package org.programers.vouchermanagement.voucher.dto.response;

import org.programers.vouchermanagement.voucher.domain.Voucher;
import org.programers.vouchermanagement.voucher.domain.VoucherPolicy;
import org.programers.vouchermanagement.voucher.domain.VoucherType;

import java.util.UUID;

public class VoucherResponse {

    private final UUID id;
    private final VoucherType type;

    private final int value;

    public VoucherResponse(Voucher voucher) {
        this(voucher.getId(), voucher.getType(), voucher.getPolicy().getValue());
    }

    public VoucherResponse(UUID id, VoucherType type, int value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
