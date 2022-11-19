package org.programmers.program.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IOTest {
    @Test
    @DisplayName("Message 문자열 반환 테스트")
    void messageTest(){
        Message m = Message.WELCOME_MESSAGE;
        assertThat(m.getMessage()).isEqualTo("안녕!");
    }
}