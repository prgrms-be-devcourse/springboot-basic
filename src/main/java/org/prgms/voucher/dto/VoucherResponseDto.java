package org.prgms.voucher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgms.voucher.voucher.VoucherPolicy;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VoucherResponseDto {
    private long amount = 0;
    private UUID id;
    private VoucherPolicy voucherPolicy;
}
