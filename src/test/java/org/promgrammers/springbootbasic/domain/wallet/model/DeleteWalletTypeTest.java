package org.promgrammers.springbootbasic.domain.wallet.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteWalletTypeTest {

    @ParameterizedTest
    @DisplayName("생성 성공 - 올바른 커맨드 입력")
    @CsvSource(
            {"1, WALLET_ID", "2, ALL"})
    void createCommandSuccessTest(String inputCommand, DeleteWalletType expectedCommand) {

        //given -> when
        DeleteWalletType deleteWalletType = assertDoesNotThrow(() -> DeleteWalletType.from(inputCommand));

        //then
        assertThat(deleteWalletType).isEqualTo(expectedCommand);
    }

    @DisplayName("생성 실패 - 올바르지 않은 커맨드 입력")
    @ParameterizedTest
    @ValueSource(strings = {"walletid", "all", "3", "", " "})
    void createCommandFailTest(String input) throws Exception {

        assertThrows(IllegalArgumentException.class, () -> DeleteWalletType.from(input));
    }

}