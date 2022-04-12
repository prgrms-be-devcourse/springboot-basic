package com.mountain.voucherApp.engine.exit;

import com.mountain.voucherApp.io.Console;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.*;

class DefaultExitTest {

    @Test
    @Description("exit 기능 정상작동 확인.")
    void testExit() {
        DefaultExit defaultExit = new DefaultExit(new Console());
        Assertions.assertDoesNotThrow(() -> defaultExit.exit());
    }

}