package com.programmers.voucher;

import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.global.io.ConsoleCommandType;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucher;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuTest {

    @InjectMocks
    private ConsoleMenu consoleMenu;

    @Mock
    private VoucherController voucherController;

    @Mock
    private Console console;

    @Test
    @DisplayName("create 명령 입력 - 성공")
    public void commandTypeCreate() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);

        VoucherCreateRequest input = new VoucherCreateRequest(VoucherType.FIXED_AMOUNT, 10);
        given(console.inputVoucherCreateInfo()).willReturn(input);
        given(voucherController.createVoucher(any())).willReturn(UUID.randomUUID());

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should().inputVoucherCreateInfo();
        then(voucherController).should().createVoucher(input);
    }

    @Test
    @DisplayName("list 명령 입력 - 성공")
    void commandTypeList() {
        //given
        Voucher fixedVoucher1 = createFixedVoucher(UUID.randomUUID(), 10);
        Voucher fixedVoucher2 = createFixedVoucher(UUID.randomUUID(), 10);
        List<Voucher> testVouchers = List.of(fixedVoucher1, fixedVoucher2);

        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.LIST);
        given(voucherController.findVouchers()).willReturn(testVouchers);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(voucherController).should().findVouchers();
    }
}