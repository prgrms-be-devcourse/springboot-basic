package com.programmers.voucher.controller;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.service.VoucherService;
import com.programmers.voucher.testutil.VoucherTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucher;
import static org.junit.jupiter.api.Assertions.*;
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
    public void commandType_Create() {
        //given
        given(console.init()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("fixed");
        given(console.intInput(anyString())).willReturn(10);

        given(voucherService.createVoucher(any())).willReturn(UUID.randomUUID());

        //when
        consoleClient.start();

        //then
    }

    @Test
    void commandType_List() {
        //given
        Voucher fixedVoucher1 = createFixedVoucher(UUID.randomUUID(), 10);
        Voucher fixedVoucher2 = createFixedVoucher(UUID.randomUUID(), 10);
        List<Voucher> testVouchers = List.of(fixedVoucher1, fixedVoucher2);

        given(console.init()).willReturn(ConsoleCommandType.LIST);
        given(voucherService.findVouchers()).willReturn(testVouchers);

        //when
        consoleClient.start();

        //then

    }

}