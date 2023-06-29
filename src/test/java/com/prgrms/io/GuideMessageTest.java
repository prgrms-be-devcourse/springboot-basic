package com.prgrms.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuideMessageTest {

    @Test
    void testToString() {
        GuideMessage startMessage = GuideMessage.START;
        System.out.println(startMessage);


        GuideMessage closeMessage = GuideMessage.CLOSE;
        System.out.println(closeMessage);

        GuideMessage completeCreateMessage = GuideMessage.COMPLETE_CREATE;
        System.out.println(completeCreateMessage);
    }

}