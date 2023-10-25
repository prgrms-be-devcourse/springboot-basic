package com.programmers.vouchermanagement.dto.voucher;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateVoucherRequestDto {
    private UUID id;
    private long amount;
}
