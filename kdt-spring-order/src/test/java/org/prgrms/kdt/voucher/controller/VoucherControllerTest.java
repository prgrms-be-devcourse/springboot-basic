package org.prgrms.kdt.voucher.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.exception.ExceptionMessage;
import org.prgrms.kdt.view.cmd.CommandLineView;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
class VoucherControllerTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.kdt.voucher.repository", "org.prgrms.kdt.voucher.service"}
    )
    static class Config{
    }

    @Autowired
    VoucherService voucherService;

    @AfterEach
    void afterEach() {
        voucherService.clearAllVouchers();
    }

    @Test
    @DisplayName("유효하지 않은 커맨드가 입력되면 오류 메시지를 출력하고 커맨드를 다시 입력받는다.")
    void testInvalidCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("1", "exit");

        //when
        voucherController.start();

        //then
        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).showErrorMessage(ExceptionMessage.INVALID_COMMAND.getMessage());
    }

    @Test
    @DisplayName("숫자가 아닌 할인 값을 이용하여 Create 커맨드 실행하면 오류 메시지를 출력하고 커맨드를 다시 입력받는다.")
    void testCreateCommandUsingInvalidDiscountValue() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("1");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("AAAA");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), is(0));

        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).requestVoucherType();
        verify(commandLineViewMock).requestDiscountValue();
        verify(commandLineViewMock).showErrorMessage(ExceptionMessage.NUMBER_FORMAT_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("Create 커맨드로 FixedVoucher를 생성한다.")
    void testFixedVoucherCreateCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("1");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("100");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), greaterThan(0));

        Voucher newVoucher = voucherService.getAllVouchers().get(0);
        assertThat(newVoucher.getClass(), is(FixedAmountVoucher.class));
        assertThat(newVoucher.discount(200), is(100L));

        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).requestVoucherType();
        verify(commandLineViewMock).requestDiscountValue();
    }

    @Test
    @DisplayName("FixedVoucher 생성 시 유효하지 않은 할인 값을 입력하면 오류 메시지를 출력하고 커맨드를 다시 입력받는다.")
    void testFixedVoucherCreateCommandUsingInvalidNumber() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create", "create", "create", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("1");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("-1000", "0", "1000001");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), is(0));

        verify(commandLineViewMock, times(4)).requestCommand();
        verify(commandLineViewMock, times(3)).requestVoucherType();
        verify(commandLineViewMock, times(3)).requestDiscountValue();
        verify(commandLineViewMock).showErrorMessage("Amount should be positive");
        verify(commandLineViewMock).showErrorMessage("Amount should not be zero");
        verify(commandLineViewMock).showErrorMessage("Amount should be less than 1000000");
    }

    @Test
    @DisplayName("Create 커맨드로 PercentVoucher를 생성한다.")
    void testPercentVoucherCreateCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("2");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("10");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), greaterThan(0));

        Voucher newVoucher = voucherService.getAllVouchers().get(0);
        assertThat(newVoucher.getClass(), is(PercentDiscountVoucher.class));
        assertThat(newVoucher.discount(200), is(20L));

        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).requestVoucherType();
        verify(commandLineViewMock).requestDiscountValue();
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성 시 유효하지 않은 할인률을 입력하면 오류 메시지를 출력하고 커맨드를 다시 입력받는다.")
    void testPercentVoucherCreateCommandUsingInvalidPercent() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("create", "create", "exit");
        when(commandLineViewMock.requestVoucherType()).thenReturn("2");
        when(commandLineViewMock.requestDiscountValue()).thenReturn("101", "-10");

        //when
        voucherController.start();

        //then
        assertThat(voucherService.getAllVouchers().size(), is(0));

        verify(commandLineViewMock, times(3)).requestCommand();
        verify(commandLineViewMock, times(2)).requestVoucherType();
        verify(commandLineViewMock, times(2)).requestDiscountValue();
        verify(commandLineViewMock, times(2)).showErrorMessage(ExceptionMessage.INVALID_VOUCHER_PERCENT_RANGE.getMessage());
    }

    @Test
    @DisplayName("List 커맨드를 입력하면 Voucher 리스트를 출력한다.")
    void testListCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("list", "exit");

        //when
        voucherController.start();

        //then
        verify(commandLineViewMock, times(2)).requestCommand();
        verify(commandLineViewMock).showVoucherList(voucherService.getAllVouchers());
    }

    @Test
    @DisplayName("Exit 커맨드를 입력하면 프로그램을 종료한다.")
    void testExitCommand() {
        //given
        CommandLineView commandLineViewMock = mock(CommandLineView.class);
        VoucherController voucherController = new VoucherController(voucherService, commandLineViewMock);
        when(commandLineViewMock.requestCommand()).thenReturn("exit");

        //when
        voucherController.start();

        //then
        verify(commandLineViewMock).requestCommand();
        verify(commandLineViewMock).close();
    }
}