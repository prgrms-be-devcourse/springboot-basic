package org.prgrms.deukyun.voucherapp.app.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.prgrms.deukyun.voucherapp.app.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.VoucherFactory;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.*;

class CreateVoucherCommandTest {

    CreateVoucherCommand command;
    VoucherService mockVoucherService;
    VoucherFactory voucherFactory;
    ConsoleService mockConsole;

    @BeforeEach
    void setup() {
        mockVoucherService = mock(VoucherService.class);
        mockConsole = mock(ConsoleService.class);
        command = new CreateVoucherCommand(mockVoucherService, mockConsole);
    }

    @Test
    void 바우처_생성_실패_InvalidType_입력() {
        //setup
        when(mockConsole.readLine()).thenReturn("나는 Invalid Type");

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> command.createVoucher());
    }

    @Test
    void 정액_할인_바우처_생성_성공() {
        //setup
        when(mockConsole.readLine()).thenReturn("fixed");
        when(mockConsole.readLong()).thenReturn(1000L);

        //action
        command.createVoucher();

        //verify
        InOrder inOrder = inOrder(mockConsole, mockConsole, mockVoucherService);

        inOrder.verify(mockConsole).write("enter the amount/percent");
        inOrder.verify(mockConsole).readLong();
        inOrder.verify(mockVoucherService).insert(any(FixedAmountDiscountVoucher.class));
    }
    

    @Nested
    class 정률_할인_바우처_생성 {

        @Test
        void 성공() {
            //setup
            when(mockConsole.readLine()).thenReturn("percent");
            when(mockConsole.readLong()).thenReturn(20L);

            //action
            command.createVoucher();

            //verify
            InOrder inOrder = inOrder(mockConsole, mockConsole, mockVoucherService);

            inOrder.verify(mockConsole).write("enter the amount/percent");
            inOrder.verify(mockConsole).readLong();
            inOrder.verify(mockVoucherService).insert(any(PercentDiscountVoucher.class));
        }

        @Test
        void 실패_범위_밖의_입력() {
            //setup
            when(mockConsole.readLine()).thenReturn("percent");
            when(mockConsole.readLong()).thenReturn(101L);

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> command.createVoucher());
        }
    }
}