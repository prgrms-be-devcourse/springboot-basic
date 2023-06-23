package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.service.VoucherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucher;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConsoleClientTest {

    @InjectMocks
    private ConsoleClient consoleClient;

    @Mock
    private VoucherService voucherService;

    @Mock
    private Console console;

    @Test
    public void commandTypeCreate() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("fixed");
        given(console.intInput(anyString())).willReturn(10);

        given(voucherService.createVoucher(any())).willReturn(UUID.randomUUID());

        //when
        consoleClient.runClient();

        //then
    }

    @Test
    void commandTypeCreate_ButInvalidVoucherType_Then_Exception() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("invalid");

        //when

        //then
        assertThatThrownBy(() -> consoleClient.runClient())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void commandTypeCreate_VoucherTypeFixed_ButInvalidAmount_Then_Exception() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("fixed");
        given(console.intInput(anyString())).willReturn(-1);

        //when

        //then
        assertThatThrownBy(() -> consoleClient.runClient())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "-1", "101"
    })
    void commandTypeCreate_VoucherTypePercent_ButInvalidAmount_Then_Exception(int amount) {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("percent");
        given(console.intInput(anyString())).willReturn(amount);

        //when

        //then
        assertThatThrownBy(() -> consoleClient.runClient())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void commandTypeList() {
        //given
        Voucher fixedVoucher1 = createFixedVoucher(UUID.randomUUID(), 10);
        Voucher fixedVoucher2 = createFixedVoucher(UUID.randomUUID(), 10);
        List<Voucher> testVouchers = List.of(fixedVoucher1, fixedVoucher2);

        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.LIST);
        given(voucherService.findVouchers()).willReturn(testVouchers);

        //when
        consoleClient.runClient();

        //then

    }

}