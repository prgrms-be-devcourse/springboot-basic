package com.programmers.springweekly.domain.wallet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletTest {

    @Test
    @DisplayName("바우처 지갑을 생성할 수 있다.")
    void createWallet() {
        // given && when
        Wallet wallet = Wallet.builder()
                .walletId(UUID.randomUUID())
                .customerId(UUID.randomUUID())
                .voucherId(UUID.randomUUID())
                .build();

        // then
        assertThat(wallet).isInstanceOf(Wallet.class);
    }

}
