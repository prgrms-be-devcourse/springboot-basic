package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.global.io.textio.TextIoConsole;
import com.programmers.voucher.global.io.textio.TextIoInput;
import com.programmers.voucher.global.io.textio.TextIoOutput;
import org.beryx.textio.TextIO;
import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TextIoConsoleTest {

    private MockTextTerminal mockTextTerminal;
    private TextIO textIO;

    private TextIoConsole textIoConsole;
    private TextIoInput textIoInput;
    private TextIoOutput textIoOutput;

    @BeforeEach
    void init() {
        mockTextTerminal = new MockTextTerminal();
        textIO = new TextIO(mockTextTerminal);

        textIoInput = new TextIoInput(textIO);
        textIoOutput = new TextIoOutput(textIO);

        textIoConsole = new TextIoConsole(textIoInput, textIoOutput);
    }


    @Test
    @DisplayName("성공: 바우처 생성 정보 입력 실행 - 바우처 타입(fixed)")
    void inputVoucherCreateInfo_VoucherTypeFixed() {
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
    @DisplayName("성공: 바우처 생성 정보 입력 실행 - 바우처 타입(percent)")
    void inputVoucherCreateInfo_VoucherTypePercent() {
        //given
        List<String> inputs = mockTextTerminal.getInputs();
        inputs.add("percent");
        inputs.add("10");

        //when
        VoucherCreateRequest result = textIoConsole.inputVoucherCreateInfo();

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
        VoucherCreateRequest result = textIoConsole.inputVoucherCreateInfo();

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
        VoucherCreateRequest result = textIoConsole.inputVoucherCreateInfo();

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
        VoucherCreateRequest result = textIoConsole.inputVoucherCreateInfo();

        //then
        assertThat(result.getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(result.getAmount()).isEqualTo(10);
    }

    @Test
    @DisplayName("성공 - 회원 목록 출력")
    void printCustomers() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB");
        List<Customer> givenCustomers = List.of(customerA, customerB);

        //when
        textIoConsole.printCustomers(givenCustomers);

        //then
        String expectedOutput = customerA.fullInfoString() + "\n" + customerB.fullInfoString();
        String output = mockTextTerminal.getOutput();

        assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    void printVouchers() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        PercentDiscountVoucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        List<Voucher> givenVouchers = List.of(fixedVoucher, percentVoucher);

        //when
        textIoConsole.printVouchers(givenVouchers);

        //then
        String expectedOutput = fixedVoucher.fullInfoString() + "\n" + percentVoucher.fullInfoString();
        String output = mockTextTerminal.getOutput();

        assertThat(output).isEqualTo(expectedOutput);
    }
}