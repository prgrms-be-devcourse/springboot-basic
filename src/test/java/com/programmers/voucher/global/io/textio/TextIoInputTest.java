package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

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

    @ParameterizedTest
    @CsvSource({
            "exit", "help", "customer", "voucher"
    })
    @DisplayName("성공: 콘솔 명령어 입력 실행")
    void inputInitialCommand(String input) {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add(input);

        //when
        textIoInput.inputInitialCommand();

        //then
        int readCalls = mockTextTerminal.getReadCalls();
        assertThat(readCalls).isEqualTo(1);
    }

    @Test
    @DisplayName("예외: 콘솔 명령어 입력 실행 - 잘못된 콘솔 명령어")
    void inputInitialCommand_ButInvalidCommandType_Then_Exception() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("invalid");

        //when
        //then
        assertThatThrownBy(() -> textIoInput.inputInitialCommand())
                .isInstanceOf(IllegalArgumentException.class);
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
    @DisplayName("성공: 바우처 생성 정보 입력 실행 - 바우처 타입(percent) - 잘못된 할인률 - 재 입력")
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

    @Test
    @DisplayName("성공: 회원 생성 정보 입력 실행")
    void inputCustomerCreateInfo() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("customer@gmail.com");
        inputs.add("customer");

        //when
        CustomerCreateRequest result = textIoInput.inputCustomerCreateInfo();

        //then
        assertThat(result.getEmail()).isEqualTo("customer@gmail.com");
        assertThat(result.getName()).isEqualTo("customer");
    }

    @Test
    @DisplayName("성공: 회원 업데이트 정보 입력 실행")
    void inputCustomerUpdateInfo() {
        //given
        UUID customerId = UUID.randomUUID();

        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add(customerId.toString());
        inputs.add("customer");

        //when
        CustomerUpdateRequest result = textIoInput.inputCustomerUpdateInfo();

        //then
        assertThat(result.getCustomerId()).isEqualTo(customerId);
        assertThat(result.getName()).isEqualTo("customer");
    }

    @Test
    @DisplayName("성공: 아이디 입력 실행")
    void inputUUID() {
        //given
        UUID uuid = UUID.randomUUID();

        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add(uuid.toString());

        //when
        UUID result = textIoInput.inputUUID();

        //then
        assertThat(result).isEqualTo(uuid);
    }
}