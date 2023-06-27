package com.programmers.voucher.io;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.global.io.TextIoConsole;
import org.beryx.textio.TextIO;
import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TextIoConsoleTest {

    private MockTextTerminal mockTextTerminal;
    private TextIO textIO;

    private TextIoConsole textIoConsole;

    @BeforeEach
    void init() {
        mockTextTerminal = new MockTextTerminal();
        textIO = new TextIO(mockTextTerminal);

        textIoConsole = new TextIoConsole(textIO);
    }


    @Test
    @DisplayName("VoucherCreateRequest 생성 입력 실행 - 성공")
    void inputVoucherCreateInfo() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("fixed");
        inputs.add("10");

        //when
        VoucherCreateRequest result = textIoConsole.inputVoucherCreateInfo();

        //then
        assertThat(result.getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
        assertThat(result.getAmount()).isEqualTo(10);
    }

    @Test
    @DisplayName("VoucherCreateRequest 생성 입력 실행 - 잘못된 바우처 타입 - 예외 발생")
    void inputVoucherCreateInfo_ButInvalidVoucherType_Then_Exception() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("invalid");

        //when
        //then
        assertThatThrownBy(() -> textIoConsole.inputVoucherCreateInfo())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("VoucherCreateRequest 생성 입력 실행 - fixed 입력 - 잘못된 할인값 - 예외 발생")
    void inputVoucherCreateInfo_VoucherTypeFixed_ButInvalidAmount_Then_Exception() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("fixed");
        inputs.add("-1");

        //when
        //then
        assertThatThrownBy(() -> textIoConsole.inputVoucherCreateInfo())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "-1", "101"
    })
    @DisplayName("VoucherCreateRequest 생성 입력 실행 - percent 입력 - 잘못된 할인값 - 예외 발생")
    void inputVoucherCreateInfo_VoucherTypePercent_ButInvalidAmount_Then_Exception(String amount) {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("percent");
        inputs.add(amount);

        //when
        //then
        assertThatThrownBy(() -> textIoConsole.inputVoucherCreateInfo())
                .isInstanceOf(IllegalArgumentException.class);
    }
}