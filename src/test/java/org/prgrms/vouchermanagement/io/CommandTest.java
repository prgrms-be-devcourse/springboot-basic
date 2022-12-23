package org.prgrms.vouchermanagement.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.prgrms.vouchermanagement.exception.command.InCorrectCommandException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandTest {

    @ParameterizedTest
    @MethodSource(value = "commandArguments")
    @DisplayName("입력값이 유효한 값인 경우 Command 반환 확인")
    void correctInputMatch(String noneInput, String exitInput, String createInput, String listInput, String blackListInput, String createCustomerInput, String customerListInput) {

        // when
        Command noneCommand = Command.findCommand(noneInput);
        Command exitCommand = Command.findCommand(exitInput);
        Command createCommand = Command.findCommand(createInput);
        Command listCommand = Command.findCommand(listInput);
        Command blackListCommand = Command.findCommand(blackListInput);
        Command createCustomerCommand = Command.findCommand(createCustomerInput);
        Command customerListCommand = Command.findCommand(customerListInput);

        // then
        assertThat(noneCommand).isEqualTo(Command.NONE);
        assertThat(exitCommand).isEqualTo(Command.EXIT);
        assertThat(createCommand).isEqualTo(Command.CREATE);
        assertThat(listCommand).isEqualTo(Command.LIST);
        assertThat(blackListCommand).isEqualTo(Command.BLACKLIST);
        assertThat(createCustomerCommand).isEqualTo(Command.CREATE_CUSTOMER);
        assertThat(customerListCommand).isEqualTo(Command.CUSTOMER_LIST);
    }

    private static Stream<Arguments> commandArguments() {
        return Stream.of(
                Arguments.of("none", "exit", "create", "list", "blacklist", "create customer", "show customers")
        );
    }

    @Test
    @DisplayName("입력값이 유효하지 않는 값인 경우 예외 확인")
    void abnormalInputNotMatch() {
        // given
        String abnormalInput = "";

        // when
        assertThrows(InCorrectCommandException.class, () -> Command.findCommand(abnormalInput));
    }
}