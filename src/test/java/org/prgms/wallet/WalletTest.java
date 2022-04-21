package org.prgms.wallet;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class WalletTest {

    @Test
    @DisplayName("Null 값이 있으면 생성이 안됨")
    void createWalletTest() {
        Assertions.assertThatThrownBy(() -> new Wallet(null, null, null)).isInstanceOf(NullPointerException.class);
        Assertions.assertThatThrownBy(() -> new Wallet(UUID.randomUUID(), null, null)).isInstanceOf(NullPointerException.class);
        Assertions.assertThatThrownBy(() -> new Wallet(UUID.randomUUID(), UUID.randomUUID(), null)).isInstanceOf(NullPointerException.class);
    }
}