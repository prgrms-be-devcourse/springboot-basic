package org.prgrms.vouchermanager.domain.wallet.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;

class WalletTest {

    @Test
    @DisplayName("생성자 customerId는 null이 될 수 없다")
    void 생성자_customerId는_null이_될_수_없다() {
        assertThatNullPointerException().isThrownBy(() -> new Wallet(null, UUID.randomUUID()));
    }

    @Test
    @DisplayName("생성자 voucherId는 null이 될 수 없다")
    void 생성자_voucherId는_null이_될_수_없다() {
        assertThatNullPointerException().isThrownBy(() -> new Wallet(UUID.randomUUID(), null));
    }
}