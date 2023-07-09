package com.example.demo.voucher.presentation.command;

import com.example.demo.common.io.Output;
import com.example.demo.voucher.application.VoucherService;
import com.example.demo.voucher.domain.FixedAmountVoucher;
import com.example.demo.voucher.domain.PercentDiscountVoucher;
import com.example.demo.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListCommandTest {

    private Output output;
    private VoucherService voucherService;
    private ListCommand listCommand;

    @BeforeEach
    public void setUp() {
        output = Mockito.mock(Output.class);
        voucherService = Mockito.mock(VoucherService.class);
        listCommand = new ListCommand(output, voucherService);
    }

    @Test
    @DisplayName("list 명령 테스트")
    public void testExecute() {
        // given
        List<Voucher> mockVouchers = Arrays.asList(
                new FixedAmountVoucher(UUID.randomUUID(), 100L),
                new PercentDiscountVoucher(UUID.randomUUID(), 20L)
        );
        when(voucherService.listVouchers()).thenReturn(mockVouchers);

        // when
        listCommand.execute();

        // then
        verify(output).printLine("Vouchers:");
        for (Voucher voucher : mockVouchers) {
            verify(output).printLine(voucher.getName() + ": " + voucher.getValue());
        }
    }
}
