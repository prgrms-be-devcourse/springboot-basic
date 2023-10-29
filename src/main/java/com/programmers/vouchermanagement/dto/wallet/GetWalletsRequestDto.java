package com.programmers.vouchermanagement.dto.wallet;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWalletsRequestDto {
    private UUID customerId;
    private UUID voucherId;
    private Boolean used;
}
