package org.prgms.voucher.dto;

import lombok.Builder;
import lombok.Getter;
import org.prgms.voucher.voucher.VoucherPolicy;

import java.text.MessageFormat;
import java.util.UUID;

@Builder
@Getter
public class VoucherResponseDto {
    private final long amount;
    private final UUID id;
    private final VoucherPolicy voucherPolicy;

    @Override
    public String toString() {
        return MessageFormat
                .format("id: {0}, voucher policy: {1}, amount: {2}", id, voucherPolicy, amount);
    }
}
