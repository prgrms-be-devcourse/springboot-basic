package org.prgrms.springorder.global;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConsoleRunningStatusTest {


    @DisplayName("isStop 메소드를 호출하면 isRunning boolean 변수가 false로 반환된다.")
    @Test
    void isStopTest() {
        //given
        boolean initialValue = ConsoleRunningStatus.isRunning();

        //when
        ConsoleRunningStatus.stop();
        boolean changedValue = ConsoleRunningStatus.isRunning();
        //then
        assertTrue(initialValue);
        assertFalse(changedValue);
    }

}