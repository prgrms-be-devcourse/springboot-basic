package org.prgms.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class WalletTest {

    @ParameterizedTest
    @DisplayName("Wallet Id null 일 수 없음")
    @NullSource
    void notNullWalletId(UUID walletId) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Wallet(walletId, UUID.randomUUID(), UUID.randomUUID()));
    }

    @ParameterizedTest
    @DisplayName("Customer Id null 일 수 없음")
    @NullSource
    void notNullCustomerId(UUID customerId) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Wallet(UUID.randomUUID(), customerId, UUID.randomUUID()));
    }

    @ParameterizedTest
    @DisplayName("Wallet Id null 일 수 없음")
    @NullSource
    void notNullVoucherId(UUID voucherId) {
        assertThatIllegalArgumentException().isThrownBy(() -> new Wallet(UUID.randomUUID(), UUID.randomUUID(), voucherId));
    }
}