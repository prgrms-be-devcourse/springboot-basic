package org.prgrms.deukyun.voucherapp.app.menu.main.exit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExitMenuTest {

    ExitButton exitButton = new ExitButton();
    ExitMenu exitMenu = new ExitMenu(exitButton);

    @Test
    void givenConstructedExitMenu_whenCheckIsExit_thenIsNotExit(){
        //assert
        assertThat(exitButton.isExit()).isFalse();
    }

    @Test
    void givenConstructedExitMenu_whenCallProcAndCheckIsExit_thenIsExit(){
        //action
        exitMenu.proc();

        //assert
        assertThat(exitButton.isExit()).isTrue();
    }
}