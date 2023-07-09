package org.promgrammers.springbootbasic.domain.customer.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindCustomerTypeTest {

    @ParameterizedTest
    @DisplayName("생성 성공 - 올바른 커맨드 입력")
    @CsvSource(
            {"1, ID", "2, USERNAME", "3,BLACKLIST"})
    void findCustomerTypeSuccessTest(String inputCommand, FindCustomerType expectedCommand) {

        //given -> when
        FindCustomerType findCustomerType = assertDoesNotThrow(() -> FindCustomerType.from(inputCommand));

        //then
        assertThat(findCustomerType).isEqualTo(expectedCommand);
    }

    @DisplayName("생성 실패 - 올바르지 않은 커맨드 입력")
    @ParameterizedTest
    @ValueSource(strings = {"find", "id", "4", "&^^", "", " "})
    void createCommandFailTest(String input) throws Exception {

        assertThrows(IllegalArgumentException.class, () -> FindCustomerType.from(input));
    }

}