package com.programmers.voucher;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.enumtype.ConsoleCommandType;
import com.programmers.voucher.io.Console;
import com.programmers.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuTest {

    @InjectMocks
    private ConsoleMenu consoleMenu;

    @Mock
    private VoucherService voucherService;

    @Mock
    private Console console;

    @Test
    @DisplayName("create 명령 입력 - 성공")
    public void commandTypeCreate() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("fixed");
        given(console.intInput(anyString())).willReturn(10);

        given(voucherService.createVoucher(any())).willReturn(UUID.randomUUID());

        //when
        consoleMenu.runClient();

        //then
    }

    @Test
    @DisplayName("create 명령 입력 - 잘못된 바우처 타입 - 예외 발생")
    void commandTypeCreate_ButInvalidVoucherType_Then_Exception() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("invalid");

        //when

        //then
        assertThatThrownBy(() -> consoleMenu.runClient())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("create 명령 입력 - fixed 바우처 타입 - 잘못된 할인값 - 예외 발생")
    void commandTypeCreate_VoucherTypeFixed_ButInvalidAmount_Then_Exception() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("fixed");
        given(console.intInput(anyString())).willReturn(-1);

        //when

        //then
        assertThatThrownBy(() -> consoleMenu.runClient())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "-1", "101"
    })
    @DisplayName("create 명령 입력 - percent 바우처 타입 - 잘못된 할인값 - 예외 발생")
    void commandTypeCreate_VoucherTypePercent_ButInvalidAmount_Then_Exception(int amount) {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);
        given(console.input(anyString())).willReturn("percent");
        given(console.intInput(anyString())).willReturn(amount);

        //when

        //then
        assertThatThrownBy(() -> consoleMenu.runClient())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("list 명령 입력 - 성공")
    void commandTypeList() {
        //given
        Voucher fixedVoucher1 = createFixedVoucher(UUID.randomUUID(), 10);
        Voucher fixedVoucher2 = createFixedVoucher(UUID.randomUUID(), 10);
        List<Voucher> testVouchers = List.of(fixedVoucher1, fixedVoucher2);

        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.LIST);
        given(voucherService.findVouchers()).willReturn(testVouchers);

        //when
        consoleMenu.runClient();

        //then

    }

}