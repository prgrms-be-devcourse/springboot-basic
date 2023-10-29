package com.programmers.springbootbasic.domain.wallet.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WalletControllerRequestDto {
    private final String email;
    private final String voucherId;
}
