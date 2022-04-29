package com.example.voucherproject.wallet;

import com.example.voucherproject.wallet.model.Wallet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.example.voucherproject.wallet.model.Wallet.create;
import static org.assertj.core.api.Assertions.assertThat;

public class WalletTest {
    @Nested
    @DisplayName("지갑 생성 테스트")
    class UserFactoryTest {

        @Test
        @DisplayName("[생성 후 값 검증] userId, voucherId ")
        void createTest(){
            UUID userId = UUID.randomUUID();
            UUID voucherId = UUID.randomUUID();

            var wallet = create(userId, voucherId);

            assertThat(wallet).isInstanceOf(Wallet.class);
            assertThat(wallet.getUserId()).isEqualTo(userId);
            assertThat(wallet.getVoucherId()).isEqualTo(voucherId);
        }

    }

}
