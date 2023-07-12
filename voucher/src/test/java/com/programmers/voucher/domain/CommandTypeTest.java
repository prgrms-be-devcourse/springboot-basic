package com.programmers.voucher.domain;

import com.programmers.voucher.domain.enums.CommandType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CommandTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"voucher", "customer", "black", "exit"})
    void validateInput(String string) {
        Optional<CommandType> optionalCommandType = CommandType.convertStringToCommandType(string);
        switch (string) {
            case "voucher" -> assertThat(optionalCommandType.get()).isSameAs(CommandType.VOUCHER);
            case "customer" -> assertThat(optionalCommandType.get()).isSameAs(CommandType.CUSTOMER);
            case "black" -> assertThat(optionalCommandType.get()).isSameAs(CommandType.BLACKLIST);
            case "exit" -> assertThat(optionalCommandType.get()).isSameAs(CommandType.EXIT);
        }
    }

    @Test
    @DisplayName("유효하지 않은 Input 값이 들어왔을 때에는 Optional.empty가 반환된다.")
    void invalidInput() {
        Assertions.assertThat(CommandType.convertStringToCommandType("invalid_text")).isEmpty();
    }

}
