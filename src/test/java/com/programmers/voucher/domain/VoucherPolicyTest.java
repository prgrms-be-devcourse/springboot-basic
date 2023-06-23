package com.programmers.voucher.domain;


import com.programmers.console.exception.VoucherCommandException;
import com.programmers.console.util.VoucherPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherPolicyTest {

    @DisplayName("바우처 생성 커맨드에 대한 테스트")
    @ParameterizedTest
    @ValueSource(strings = {
            "1"
            , "2"
            , "3"
            , "0"
            , ""
    })
    public void createVoucherTest(String command) {
        LocalDate localDate = LocalDate.of(2023, 06, 23);
        assertThatThrownBy(() -> VoucherPolicy.findByCommand(command).constructor(UUID.randomUUID(), 10L, localDate))
                .isInstanceOf(VoucherCommandException.class);

    }

}