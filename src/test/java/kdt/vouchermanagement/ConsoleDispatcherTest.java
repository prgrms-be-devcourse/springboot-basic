package kdt.vouchermanagement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsoleDispatcherTest {

    @InjectMocks
    ConsoleDispatcher consoleDispatcher;

    @Mock
    Input consoleInput;

    @Test
    @DisplayName("입력값(메뉴, 바우처 타입, 할인값)이 공백이면_실패")
    void nullOrEmptyInputMenu() {
        //given
        String inputValue = "";
        doReturn(inputValue).when(consoleInput).input();

        //when, then
        assertThrows(IllegalArgumentException.class, () -> consoleDispatcher.excuteInput());
    }

    @Test
    @DisplayName("입력된 메뉴가 유효하지 않은 메뉴이면_실패")
    void getInvalidMenu() {
        //given
        String inputMenu = "blahblah";

        //when, then
        assertThrows(InvalidMenuException.class, () -> consoleDispatcher.findMenu(inputMenu));
    }

    @Test
    @DisplayName("입력된 메뉴가 유효한 메뉴이면_성공")
    void getValidMenu() {
        //given
        String inputMenu = "create";

        //when
        Menu menu = consoleDispatcher.findMenu(inputMenu);

        // then
        assertThat(menu, is(Menu.CREATE_VOUCHER));
    }
}
