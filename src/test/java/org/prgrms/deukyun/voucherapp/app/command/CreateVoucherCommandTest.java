package org.prgrms.deukyun.voucherapp.app.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.prgrms.deukyun.voucherapp.app.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.*;

class CreateVoucherCommandTest {

    CreateVoucherCommand command;
    VoucherService mockVoucherService;
    ConsoleService mockConsole;

    @BeforeEach
    void setup() {
        mockVoucherService = mock(VoucherService.class);
        mockConsole = mock(ConsoleService.class);
        command = new CreateVoucherCommand(mockVoucherService, mockConsole);
    }

    @Test
    void givenInvalidTypeString_whenCallCreateVoucher_thenThrowsIllegalArgumentException() {
        //setup
        when(mockConsole.readLine()).thenReturn("나는 Invalid Type");

        //assert throws
        assertThatIllegalArgumentException()
                .isThrownBy(() -> command.createVoucher());
    }

    @Nested
    class givenTypeStringFixed_whenCallCreateVoucher_test {

        @Test
        void givenAmountString1000_thenWriteMsgAndReadLineAndInsertFADV() {
            //setup
            when(mockConsole.readLine()).thenReturn("fixed").thenReturn("1000");

            //action
            command.createVoucher();

            //verify
            InOrder inOrder = inOrder(mockConsole, mockConsole, mockVoucherService);

            inOrder.verify(mockConsole).write("enter the amount");
            inOrder.verify(mockConsole).readLine();
            inOrder.verify(mockVoucherService).insert(any(FixedAmountDiscountVoucher.class));
        }

        @Test
        void givenInvalidAmountString_thenThrowsIllegalArgumentException() {
            //setup
            when(mockConsole.readLine()).thenReturn("fixed").thenReturn("Invalid Amount");

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> command.createVoucher());
        }
    }

    @Nested
    class givenTypeStringPercent_whenCallCreateVoucher_test {

        @Test
        void givenPercentString20_thenWriteMsgAndReadLineAndInsertPDV() {
            //setup
            when(mockConsole.readLine()).thenReturn("percent").thenReturn("20");

            //action
            command.createVoucher();

            //verify
            InOrder inOrder = inOrder(mockConsole, mockConsole, mockVoucherService);

            inOrder.verify(mockConsole).write("enter the percent");
            inOrder.verify(mockConsole).readLine();
            inOrder.verify(mockVoucherService).insert(any(PercentDiscountVoucher.class));
        }

        @Test
        void givenInvalidAmountStringOutOfBounds_thenThrowsIllegalArgumentException() {
            //setup
            when(mockConsole.readLine()).thenReturn("percent").thenReturn("2000");

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> command.createVoucher());
        }

        @Test
        void givenInvalidAmountString_thenThrowsIllegalArgumentException() {
            //setup
            when(mockConsole.readLine()).thenReturn("percent").thenReturn("Invalid Percent");

            //assert throws
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> command.createVoucher());
        }
    }
}