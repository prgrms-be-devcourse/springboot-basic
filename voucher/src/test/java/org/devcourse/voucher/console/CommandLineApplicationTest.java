package org.devcourse.voucher.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineApplicationTest {
    @Test
    @DisplayName("CommandLineApplication execute 지원하지 않는 명령어 예외 처리 테스트")
    void executeExceptionTest(){
        String command = "ex";
        CommandLineApplication commandLineApplication = new CommandLineApplication();
        assertThrowsExactly(IllegalArgumentException.class, ()->{
            commandLineApplication.execute(command);
        });
    }
}
