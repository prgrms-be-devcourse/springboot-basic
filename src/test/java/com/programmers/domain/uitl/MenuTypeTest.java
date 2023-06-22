package com.programmers.domain.uitl;

import com.programmers.console.util.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThatThrownBy(() -> Command.findByCommand(command))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void test() {
        // 스캐너
        // 인풋클래스 의존성
        // 기능 구현
        // input에다가 하나의 값을 넣고

    }
}