package org.prgrms.vouchermanager.wallet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WalletTest {

    @Test
    @DisplayName("생성자 customerId는 null이 될 수 없다")
    void 생성자_customerId는_null이_될_수_없다() {
        assertThatThrownBy(() -> new Wallet(UUID.randomUUID(), null, UUID.randomUUID())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("생성자 voucherId는 null이 될 수 없다")
    void 생성자_voucherId는_null이_될_수_없다() {
        assertThatThrownBy(() -> new Wallet(UUID.randomUUID(), UUID.randomUUID(), null)).isInstanceOf(IllegalArgumentException.class);
    }
}