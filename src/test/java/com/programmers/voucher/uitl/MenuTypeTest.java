package com.programmers.voucher.uitl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuTypeTest {

    @DisplayName("MenuType 반환 테스트")
    @ParameterizedTest
    @ValueSource(strings = {
            "create"
            , "CREATE"
            , "list"
            , "List"
            , "EXIT"
            , "exit"
            , "abc"
    })
    void 메뉴타입_반환_테스트(String command) {
        assertThatThrownBy(() -> MenuType.getCommand(command))
                .isInstanceOf(RuntimeException.class);
    }
}