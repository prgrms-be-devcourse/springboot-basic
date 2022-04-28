package org.prgrms.deukyun.voucherapp.view.command;

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
    void 성공_퇴장_예외던지면_성공임() {
        //assert throws
        assertThatThrownBy(() -> command.exit())
                .isInstanceOf(ExitRequest.class);
    }
}