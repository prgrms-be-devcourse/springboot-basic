package com.programmers.voucher;

import com.programmers.voucher.global.io.menu.ConsoleMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ConsoleRunnerTest {

    @InjectMocks
    private ConsoleRunner consoleRunner;

    @Mock
    private ConsoleMenu consoleMenu;

    @Test
    @DisplayName("성공: 콘솔 애플리케이션 기동 - 1회")
    void run() {
        //given
        given(consoleMenu.runAndProcessClient()).willReturn(false);

        //when
        consoleRunner.run();

        //then
        then(consoleMenu).should(times(1)).runAndProcessClient();
    }

    @Test
    @DisplayName("성공: 콘솔 애플리케이션 기동 - 5회")
    void run_5times() {
        //given
        given(consoleMenu.runAndProcessClient())
                .willReturn(true)
                .willReturn(true)
                .willReturn(true)
                .willReturn(true)
                .willReturn(false);

        //when
        consoleRunner.run();

        //then
        then(consoleMenu).should(times(5)).runAndProcessClient();
    }
}