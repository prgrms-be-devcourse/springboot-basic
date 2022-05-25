package org.devcourse.voucher.exception;

import org.assertj.core.api.Assertions;
import org.devcourse.voucher.core.exception.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.devcourse.voucher.core.exception.ErrorType.*;

@SpringJUnitConfig
class ErrorTypeTest {

    @Test
    @DisplayName("yaml 설정 파일로부터 에러메시지를 잘 가져오는지 테스트")
    void messageTest() {
        ErrorType[] stubs = {
            INVALID_COMMAND,
            INPUT_NEGATIVE_NUMBERS,
            INPUT_NOT_NUMBERS,
        };

        for (ErrorType stub : stubs) {
            Assertions.assertThat(stub.message()).isNotNull();
        }
    }
}