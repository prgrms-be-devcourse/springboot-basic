package org.programmers.springboot.basic.domain.voucher.dto;

import lombok.Builder;
import lombok.Getter;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class VoucherResponseDto {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final Long discount;
    private final LocalDateTime createdAt;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String printInfo() {
        return String.format("""
                === Voucher ===
                VoucherID: %s
                Discount: %d
                VoucherType: %s
                CreatedAt: %s
                """, voucherId.toString(), discount, voucherType, createdAt.toString());
    }
}
