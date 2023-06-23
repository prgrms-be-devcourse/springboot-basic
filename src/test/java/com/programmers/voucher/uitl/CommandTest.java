package com.programmers.voucher.uitl;

import com.programmers.console.util.Command;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTest {

    @DisplayName("유효하지 않은 커맨드 입력값 테스트")
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
    void 유효하지_않은_커맨드_입력_테스트(String command) {
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