package org.programmers.springboot.basic.domain.wallet.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WalletControllerRequestDto {

    private final String voucherId;
    private final String email;
}
