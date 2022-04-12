package org.prgrms.kdt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exception.InputException;
import org.prgrms.kdt.repository.VoucherMemoryRepository;
import org.prgrms.kdt.service.VoucherService;

public class CommandLineApplicationTest {

    VoucherService voucherService = new VoucherService(new VoucherMemoryRepository());
    CommandLineApplication commandLineApplication = new CommandLineApplication();

    @Test
    @DisplayName("PercentDistcoutVoucher에 0미만 100초과 입력시 실패")
    public void percentInputTest() {

        //given
        int inputPercent = 111;

        //when

        //then
        Assertions.assertThatThrownBy(() -> {
                    voucherService.create(inputPercent, "percent");
                }).isInstanceOf(InputException.class)
                .hasMessageContaining("Invalid Input...");
    }

    @Test
    @DisplayName("FixedAmountVoucher 생성시 Amount에 음수 입력시 실패")
    public void amountInputTest() {

        //given
        int inputAmount = -1;

        //when

        //then
        Assertions.assertThatThrownBy(() -> {
                    voucherService.create(inputAmount, "fixed");
                }).isInstanceOf(InputException.class)
                .hasMessageContaining("Invalid Input...");
    }

    @Test
    @DisplayName("메뉴 입력시 유효하지 않은 명령어 ,유효한 명령어 = (create, list, exit)")
    public void menuInputTest() {

        //given
        String inputMenu = "aaaaa";

        //when

        //then
        Assertions.assertThatThrownBy(() -> {
                    commandLineApplication.checkInput(inputMenu);
                }).isInstanceOf(InputException.class)
                .hasMessageContaining("Invalid Input...");
    }

    @Test
    @DisplayName("유효하지 않은 바우처 타입 선택, 유효한 선택 (1, 2)")
    public void selectVoucherTest() {

        //given
        String SelectVoucherType = "3";

        //when

        //then
        Assertions.assertThatThrownBy(() -> {
                    commandLineApplication.checkInput(SelectVoucherType);
                }).isInstanceOf(InputException.class)
                .hasMessageContaining("Invalid Input...");
    }
}
