package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;

@SpringBootTest
@ActiveProfiles("test")
class MenuHandlerTest {
    @Autowired
    MockTextTerminal textTerminal;
    @Autowired
    ConsoleManager consoleManager;
    @Autowired
    MenuHandler menuHandler;

    @Test
    @DisplayName("exit을 입력할 시 해당 뷰를 출력하고 시스템을 종료한다.")
    void testHandleMenuFalse_Exit() {
        //when
        menuHandler.handleMenu(Menu.EXIT);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("System exits."));
    }

    @Test
    @DisplayName("잘못된 메뉴를 입력하면 해당 뷰를 출력하지만 시스템은 계속 된다.")
    void testHandleMenuTrue_IncorrectMenu() {
        //when
        menuHandler.handleMenu(Menu.INCORRECT_MENU);
        String output = textTerminal.getOutput();

        //then
        assertThat(output, containsString("""
                Such input is incorrect.
                Please input a correct command carefully."""));
    }
}
