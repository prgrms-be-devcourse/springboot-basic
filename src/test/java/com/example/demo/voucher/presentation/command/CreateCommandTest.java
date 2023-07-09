package com.example.demo.voucher.presentation.command;

import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import com.example.demo.voucher.application.VoucherService;
import com.example.demo.voucher.application.VoucherType;
import com.example.demo.voucher.presentation.message.VoucherTypeMessage;
import com.example.demo.voucher.presentation.message.VoucherTypeMessageMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateCommandTest {

    private Output output;
    private Input input;
    private VoucherService voucherService;
    private CreateCommand createCommand;

    @BeforeEach
    public void setUp() {
        output = Mockito.mock(Output.class);
        input = Mockito.mock(Input.class);
        voucherService = Mockito.mock(VoucherService.class);

        createCommand = new CreateCommand(output, input, voucherService);
    }

    @Test
    @DisplayName("craete 명령 테스트")
    public void testExecute() {
        // given
        String expectedVoucherTypeInput = "1";
        String expectedValueInput = "100";
        when(input.readLine()).thenReturn(expectedVoucherTypeInput, expectedValueInput);
        VoucherType expectedVoucherType = VoucherType.FIXED_AMOUNT_VOUCHER;
        long expectedValue = Long.parseLong(expectedValueInput);
        VoucherTypeMessage fixedAmountMessage = VoucherTypeMessageMapper.getInstance().getMessage(expectedVoucherType);

        // when
        createCommand.execute();

        // then
        verify(output).printLine("Please enter the voucher type:");
        verify(output).printLine(expectedVoucherType.getCounter() + " : " + fixedAmountMessage.getMessage());
        verify(voucherService).createVoucher(expectedVoucherType, expectedValue);
    }
}
