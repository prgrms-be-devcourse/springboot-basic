package kdt.vouchermanagement;

import kdt.vouchermanagement.global.io.Input;
import kdt.vouchermanagement.global.view.ConsoleDispatcher;
import kdt.vouchermanagement.global.view.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ConsoleDispatcherTest {

    @InjectMocks
    ConsoleDispatcher consoleDispatcher;

    @Mock
    Input consoleInput;

    @Test
    @DisplayName("입력된 메뉴가 유효하지 않은 메뉴이면_실패")
    void getInvalidMenu() throws Exception {
        //given
        String input = "hello";

        Method findMenu = consoleDispatcher.getClass().getDeclaredMethod("findMenu", String.class);
        findMenu.setAccessible(true);

        //then
        Object menu = findMenu.invoke(consoleDispatcher, input);

        //then
        assertThat(menu).isEqualTo(Menu.NONE);
    }

    @Test
    @DisplayName("입력된 메뉴가 유효한 메뉴이면_성공")
    void getValidMenu() throws Exception {
        //given
        String input = "create";

        Method findMenu = consoleDispatcher.getClass().getDeclaredMethod("findMenu", String.class);
        findMenu.setAccessible(true);

        //when
        Object menu = findMenu.invoke(consoleDispatcher, input);

        //then
        assertThat(menu).isEqualTo(Menu.CREATE_VOUCHER);
    }
}