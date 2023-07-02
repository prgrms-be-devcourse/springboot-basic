package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import org.beryx.textio.TextIO;
import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TextIoOutputTest {
    private MockTextTerminal mockTextTerminal;
    private TextIO textIO;

    private TextIoOutput textIoOutput;

    @BeforeEach
    void init() {
        mockTextTerminal = new MockTextTerminal();
        textIO = new TextIO(mockTextTerminal);

        textIoOutput = new TextIoOutput(textIO);
    }

    @Test
    @DisplayName("성공: 회원 목록 출력")
    void printCustomers() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB");
        List<Customer> givenCustomers = List.of(customerA, customerB);

        //when
        textIoOutput.printCustomers(givenCustomers);

        //then
        String expectedOutput = customerA.fullInfoString() + "\n" + customerB.fullInfoString();
        String output = mockTextTerminal.getOutput();

        assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("성공: 바우처 목록 출력")
    void printVouchers() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        PercentDiscountVoucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        List<Voucher> givenVouchers = List.of(fixedVoucher, percentVoucher);

        //when
        textIoOutput.printVouchers(givenVouchers);

        //then
        String expectedOutput = fixedVoucher.fullInfoString() + "\n" + percentVoucher.fullInfoString();
        String output = mockTextTerminal.getOutput();

        assertThat(output).isEqualTo(expectedOutput);
    }
}