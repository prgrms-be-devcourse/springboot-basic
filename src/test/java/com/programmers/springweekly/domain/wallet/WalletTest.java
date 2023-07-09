package com.programmers.springweekly.domain.wallet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WalletTest {

    @Test
    @DisplayName("바우처 지갑을 생성한다.")
    void createWallet() {
        // given && when
        Wallet wallet = new Wallet(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        // then
        assertThat(wallet).isInstanceOf(Wallet.class);
    }

}
