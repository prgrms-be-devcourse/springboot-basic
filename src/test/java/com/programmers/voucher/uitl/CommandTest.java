package com.programmers.voucher.uitl;

import com.programmers.console.util.Command;
import com.programmers.global.exception.VoucherCommandException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandTest {

    @DisplayName("유효하지 않은 커맨드 입력값 테스트")
    @ParameterizedTest
    @ValueSource(strings = {
            "create1"
            , "CREATE3"
            , "list4"
            , "List5"
            , "EXIT2"
            , "1exit"
            , "abc"
    })
    void 유효하지_않은_커맨드_입력_테스트(String command) {
        assertThatThrownBy(() -> Command.of(command))
                .isInstanceOf(VoucherCommandException.class);
    }
}
