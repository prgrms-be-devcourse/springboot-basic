package org.prgms.voucher.dto;

import lombok.*;
import org.prgms.voucher.voucher.VoucherPolicy;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VoucherResponseDto {
    private long amount = 0;
    private UUID id;
    private VoucherPolicy voucherPolicy;
}
