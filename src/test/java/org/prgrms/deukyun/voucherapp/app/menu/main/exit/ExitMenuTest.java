package org.prgrms.deukyun.voucherapp.app.menu.main.exit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ExitMenuTest {

    ExitButton exitButton = new ExitButton();
    ExitMenu exitMenu = new ExitMenu(exitButton);

    @Test
    void givenConstructedExitMenu_whenCheckIsDone_thenIsNotDone(){
        //assert
        assertThat(exitButton.isExit()).isFalse();
    }

    @Test
    void givenConstructedExitMenu_whenCallProcAndCheckIsDone_thenIsDone(){
        //action
        exitMenu.proc();

        //assert
        assertThat(exitButton.isExit()).isTrue();
    }
}