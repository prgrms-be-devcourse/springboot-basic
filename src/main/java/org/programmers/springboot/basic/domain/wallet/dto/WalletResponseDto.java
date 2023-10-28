package org.programmers.springboot.basic.domain.wallet.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class WalletResponseDto {

    private final String email;
    private final UUID voucherId;
}
