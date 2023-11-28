package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.List;

import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.repository.CustomerRepository;
import com.programmers.vouchermanagement.voucher.repository.VoucherRepository;

@SpringBootTest
@ActiveProfiles("test")
class MenuHandlerTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    MockTextTerminal textTerminal;
    @Autowired
    ConsoleManager consoleManager;
    @Autowired
    MenuHandler menuHandler;

    @Test
    @DisplayName("exit을 입력할 시 종료 뷰를 출력한다.")
    void testHandleMenuFalse_Exit() {
        //when
        menuHandler.handleMenu(Menu.EXIT);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("System exits."));
    }

    @Test
    @DisplayName("잘못된 메뉴를 입력하면 해당 뷰를 출력한다.")
    void testHandleMenuTrue_IncorrectMenu() {
        //when
        menuHandler.handleMenu(Menu.INCORRECT_MENU);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("""
                Such input is incorrect.
                Please input a correct command carefully."""));
    }

    @Test
    @DisplayName("없는 바우처 메뉴를 선택 시 해당 뷰를 출력한다.")
    void testExecuteVoucherMenu_IncorrectMenu() {
        //given
        textTerminal.getInputs()
                .add("9");

        //when
        menuHandler.handleMenu(Menu.VOUCHER);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("""
                Such input is incorrect.
                Please input a correct command carefully."""));
    }

    @Test
    @DisplayName("맞는 바우처 메뉴를 선택 시 해당 뷰를 출력한다.")
    void testExecuteVoucherMenu_CorrectMenu() {
        //given
        textTerminal.getInputs()
                .addAll(List.of("1", "1", "10000"));

        //when
        menuHandler.handleMenu(Menu.VOUCHER);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("successfully saved."));

        //clean
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("없는 고객 메뉴를 선택 시 해당 뷰를 출력한다.")
    void testExecuteCustomerMenu_IncorrectMenu() {
        //given
        textTerminal.getInputs()
                .add("9");

        //when
        menuHandler.handleMenu(Menu.CUSTOMER);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("""
                Such input is incorrect.
                Please input a correct command carefully."""));
    }

    @Test
    @DisplayName("맞는 고객 메뉴를 선택 시 해당 뷰를 출력한다.")
    void testExecuteCustomerMenu_CorrectMenu() {
        //given
        textTerminal.getInputs()
                .addAll(List.of("1", "tester"));

        //when
        menuHandler.handleMenu(Menu.CUSTOMER);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("successfully saved"));

        //clean
        customerRepository.deleteAll();
    }
}
