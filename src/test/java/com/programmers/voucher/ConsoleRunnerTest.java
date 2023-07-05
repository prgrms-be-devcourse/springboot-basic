package com.programmers.voucher;

import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.menu.ConsoleMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.NoSuchElementException;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ConsoleRunnerTest {

    @InjectMocks
    private ConsoleRunner consoleRunner;

    @Mock
    private Console console;

    @Mock
    private ConsoleMenu consoleMenu;

    @Test
    @DisplayName("성공: IllegalArgumentException 발생 - 예외 처리 후 재 입력")
    void run_ButThrownIllegalArgumentException_Then_KeepRunning() {
        //given
        given(consoleMenu.runClient())
                .willThrow(new IllegalArgumentException("Error message"))
                .willReturn(false);

        //when
        consoleRunner.run();

        //then
        then(consoleMenu).should(times(2)).runClient();
        then(console).should().print("Error message");
    }

    @Test
    @DisplayName("성공: NoSuchElementException 발생 - 예외 처리 후 재 입력")
    void run_ButThrownNoSuchElementException_Then_KeepRunning() {
        //given
        given(consoleMenu.runClient())
                .willThrow(new NoSuchElementException("Error message"))
                .willReturn(false);

        //when
        consoleRunner.run();

        //then
        then(consoleMenu).should(times(2)).runClient();
        then(console).should().print("Error message");
    }

    @Test
    @DisplayName("성공: DuplicateKeyException 발생 - 예외 처리 후 재 입력")
    void run_ButThrownDuplicateKeyException_Then_KeepRunning() {
        //given
        given(consoleMenu.runClient())
                .willThrow(new DuplicateKeyException("Error message"))
                .willReturn(false);

        //when
        consoleRunner.run();

        //then
        then(consoleMenu).should(times(2)).runClient();
        then(console).should().print("Error message");
    }

    @Test
    @DisplayName("성공: 예측하지 못한 예외 발생 - 예외 처리 후 종료")
    void run_ButThrownTheOtherExceptions_Then_ExitConsole() {
        //given
        given(consoleMenu.runClient()).willThrow(new RuntimeException("Error message"));

        //when
        consoleRunner.run();

        //then
        then(consoleMenu).should().runClient();
        then(console).should().print("Error message");
    }
}