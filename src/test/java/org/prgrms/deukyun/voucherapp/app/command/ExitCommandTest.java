package org.prgrms.deukyun.voucherapp.app.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.shell.ExitRequest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExitCommandTest {


    ExitCommand command;

    @BeforeEach
    void setup() {
        command = new ExitCommand();
    }

    @Test
    void givenExitCommand_whenCallExit_thenThrowsExitRequest() {
        //assert throws
        assertThatThrownBy(() -> command.exit())
                .isInstanceOf(ExitRequest.class);
    }
}