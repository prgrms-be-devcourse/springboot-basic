package org.promgrammers.springbootbasic.domain.wallet.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindWalletTypeTest {

    @ParameterizedTest
    @DisplayName("생성 성공 - 올바른 커맨드 입력")
    @CsvSource(
            {"1, WALLET_ID", "2, VOUCHER_ID", "3,CUSTOMER_ID"})
    void createCommandSuccessTest(String inputCommand, FindWalletType expectedCommand) {

        //given -> when
        FindWalletType findWalletType = assertDoesNotThrow(() -> FindWalletType.from(inputCommand));

        //then
        assertThat(findWalletType).isEqualTo(expectedCommand);
    }

    @DisplayName("생성 실패 - 올바르지 않은 커맨드 입력")
    @ParameterizedTest
    @ValueSource(strings = {"wallet", "VOUCHER", "5", "7", "&^^", "", " "})
    void createCommandFailTest(String input) throws Exception {

        assertThrows(IllegalArgumentException.class, () -> FindWalletType.from(input));
    }

}