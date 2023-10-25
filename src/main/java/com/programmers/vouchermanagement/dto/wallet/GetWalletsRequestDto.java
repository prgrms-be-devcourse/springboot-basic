package com.programmers.vouchermanagement.dto.wallet;

import lombok.Data;

import java.util.UUID;

@Data
public class GetWalletsRequestDto {
    private UUID customerId;
    private UUID voucherId;
    private Boolean used;
}
