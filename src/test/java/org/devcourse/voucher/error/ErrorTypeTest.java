package org.devcourse.voucher.error;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.devcourse.voucher.error.ErrorType.*;

@SpringBootTest
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