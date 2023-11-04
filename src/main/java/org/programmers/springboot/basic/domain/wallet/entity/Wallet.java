package org.programmers.springboot.basic.domain.wallet.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;

import java.util.UUID;

@Getter
@Builder
@RequiredArgsConstructor
public class Wallet {

    private final UUID voucherId;
    private final Email email;
}
