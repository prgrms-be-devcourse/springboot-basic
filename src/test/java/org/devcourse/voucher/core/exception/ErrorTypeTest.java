package org.devcourse.voucher.core.exception;

import org.assertj.core.api.Assertions;
import org.devcourse.voucher.core.exception.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.devcourse.voucher.core.exception.ErrorType.*;

@SpringBootTest
class ErrorTypeTest {

    @Test
    @DisplayName("yaml 설정 파일로부터 에러메시지를 잘 가져오는지 테스트")
    void messageTest() {
        Arrays.stream(values())
                .forEach(errorType -> assertThat(errorType.message()).isNotNull());
    }
}