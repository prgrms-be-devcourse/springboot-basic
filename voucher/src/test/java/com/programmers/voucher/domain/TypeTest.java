package com.programmers.voucher.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class TypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"create", "list", "exit"})
    void validateInput(String string) {
        Type type = Type.validateInput(string);
        switch (string) {
            case "create" -> assertThat(type).isSameAs(Type.CREATE);
            case "list" -> assertThat(type).isSameAs(Type.LIST);
            case "exit" -> assertThat(type).isSameAs(Type.EXIT);
        }
    }

    @Test
    void validateInput_error() {
        Assertions.assertThatThrownBy(
                () -> Type.validateInput("invalid_text")
        ).isInstanceOf(IllegalArgumentException.class);
    }

}
