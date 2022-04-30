package org.prgrms.deukyun.voucherapp.view.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.prgrms.deukyun.voucherapp.view.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.*;

class CreateVoucherCommandTest {

    CreateVoucherCommand command;
    VoucherService voucherService;
    ConsoleService console;

    @BeforeEach
    void setup() {
        voucherService = mock(VoucherService.class);
        console = mock(ConsoleService.class);
        command = new CreateVoucherCommand(voucherService, console);
    }

    @Test
    void 바우처_생성_실패_InvalidType_입력() {
        //given
        when(console.readLine()).thenReturn("나는 Invalid Type");

        //then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> command.createVoucher());
    }

    @Test
    void 정액_할인_바우처_생성_성공() {
        //given
        when(console.readLine()).thenReturn("fixed");
        when(console.readLong()).thenReturn(1000L);

        //when
        command.createVoucher();

        //verify
        InOrder inOrder = inOrder(console, console, voucherService);

        inOrder.verify(console).write("enter the amount/percent");
        inOrder.verify(console).readLong();
        inOrder.verify(voucherService).insert(any(FixedAmountDiscountVoucher.class));
    }
    

    @Nested
    class 정률_할인_바우처_생성 {

        @Test
        void 성공() {
            //given
            when(console.readLine()).thenReturn("percent");
            when(console.readLong()).thenReturn(20L);

            //when
            command.createVoucher();

            //verify
            InOrder inOrder = inOrder(console, console, voucherService);

            inOrder.verify(console).write("enter the amount/percent");
            inOrder.verify(console).readLong();
            inOrder.verify(voucherService).insert(any(PercentDiscountVoucher.class));
        }

        @Test
        void 실패_범위_밖의_입력() {
            //given
            when(console.readLine()).thenReturn("percent");
            when(console.readLong()).thenReturn(101L);

            //then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> command.createVoucher());
        }
    }
}