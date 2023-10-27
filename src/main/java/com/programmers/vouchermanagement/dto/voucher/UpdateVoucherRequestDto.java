package com.programmers.vouchermanagement.dto.voucher;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVoucherRequestDto {
    private UUID id;
    private long amount;
}
