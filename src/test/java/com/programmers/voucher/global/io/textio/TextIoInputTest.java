package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import org.beryx.textio.TextIO;
import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TextIoInputTest {
    private MockTextTerminal mockTextTerminal;
    private TextIO textIO;

    private TextIoInput textIoInput;

    @BeforeEach
    void init() {
        mockTextTerminal = new MockTextTerminal();
        textIO = new TextIO(mockTextTerminal);

        textIoInput = new TextIoInput(textIO);
    }

    @Test
    @DisplayName("성공: 바우처 생성 정보 입력 실행 - 바우처 타입(fixed)")
    void inputVoucherCreateInfo_VoucherTypeFixed() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("fixed");
        inputs.add("10");

        //when
        VoucherCreateRequest result = textIoInput.inputVoucherCreateInfo();

        //then
        assertThat(result.getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
        assertThat(result.getAmount()).isEqualTo(10);
    }

    @Test
    @DisplayName("성공: 바우처 생성 정보 입력 실행 - 바우처 타입(percent)")
    void inputVoucherCreateInfo_VoucherTypePercent() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("percent");
        inputs.add("10");

        //when
        VoucherCreateRequest result = textIoInput.inputVoucherCreateInfo();

        //then
        assertThat(result.getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(result.getAmount()).isEqualTo(10);
    }

    @Test
    @DisplayName("성공: 바우처 생성 정보 입력 실행 - 잘못된 바우처 타입 - 재 입력")
    void inputVoucherCreateInfo_ButInvalidVoucherType_Then_Rerun() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("invalid");
        inputs.add("fixed");
        inputs.add("10");

        //when
        VoucherCreateRequest result = textIoInput.inputVoucherCreateInfo();

        //then
        assertThat(result.getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
        assertThat(result.getAmount()).isEqualTo(10);
    }

    @ParameterizedTest
    @CsvSource({
            "-10", "-1", "0"
    })
    @DisplayName("성공: 바우처 생성 정보 입력 실행 - 바우처 타입(fixed) - 잘못된 할인값 - 재 입력")
    void inputVoucherCreateInfo_VoucherTypeFixed_ButInvalidAmount_Then_Rerun(String invalidAmount) {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("fixed");
        inputs.add(invalidAmount);
        inputs.add("10");

        //when
        VoucherCreateRequest result = textIoInput.inputVoucherCreateInfo();

        //then
        assertThat(result.getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
        assertThat(result.getAmount()).isEqualTo(10);
    }

    @ParameterizedTest
    @CsvSource({
            "-1", "0", "100", "101"
    })
    @DisplayName("성공: 바우처 생성 정보 입력 실행 - 바우처 타입(percent) - 잘못된 할인값 - 재 입력")
    void inputVoucherCreateInfo_VoucherTypePercent_ButInvalidAmount_Then_Rerun(String invalidAmount) {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("percent");
        inputs.add(invalidAmount);
        inputs.add("10");

        //when
        VoucherCreateRequest result = textIoInput.inputVoucherCreateInfo();

        //then
        assertThat(result.getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(result.getAmount()).isEqualTo(10);
    }
}