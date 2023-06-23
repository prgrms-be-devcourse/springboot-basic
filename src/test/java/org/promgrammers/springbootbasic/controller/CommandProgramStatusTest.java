package org.promgrammers.springbootbasic.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandProgramStatusTest {
    @Test
    @DisplayName("isStop 메서드 호출시 isRunning이 false가 된다")
    void isStopTest() throws Exception {

        //given
        boolean initialValue = CommandProgramStatus.isRunning();

        //when
        CommandProgramStatus.stop();
        boolean changedValue = CommandProgramStatus.isRunning();

        //then
        assertTrue(initialValue);
        assertFalse(changedValue);
    }
}