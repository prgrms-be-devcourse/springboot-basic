package kdt.vouchermanagement;

import kdt.vouchermanagement.exception.InvalidMenuException;
import kdt.vouchermanagement.io.Input;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConsoleDispatcherTest {

    @InjectMocks
    ConsoleDispatcher consoleDispatcher;

    @Mock
    Input consoleInput;

    @Test
    @DisplayName("입력값(메뉴, 바우처 타입, 할인값)이 공백이면_실패")
    void nullOrEmptyInputMenu() throws NoSuchMethodException {
        //given
        String input = "";
        doReturn(input).when(consoleInput).input();

        Method method = consoleDispatcher.getClass().getDeclaredMethod("excuteInput");
        method.setAccessible(true);

        //when
        try {
            method.invoke(consoleDispatcher);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            //then
            assertThat(e.getCause().getClass()).isEqualTo(IllegalArgumentException.class);
            assertThat(e.getCause().getMessage()).isEqualTo("공백이 입력되었습니다. 올바른 값을 입력해주세요.");
        }
    }

    @Test
    @DisplayName("입력된 메뉴가 유효하지 않은 메뉴이면_실패")
    void getInvalidMenu() throws NoSuchMethodException {
        //given
        String input = "hello";

        Method method = consoleDispatcher.getClass().getDeclaredMethod("findMenu", String.class);
        method.setAccessible(true);

        //when
        try {
            method.invoke(consoleDispatcher, input);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            //then
            assertThat(e.getCause().getClass()).isEqualTo(InvalidMenuException.class);
            assertThat(e.getCause().getMessage()).isEqualTo("입력한 메뉴값이 유효하지 않습니다.");
        }
    }

    @Test
    @DisplayName("입력된 메뉴가 유효한 메뉴이면_성공")
    void getValidMenu() throws NoSuchMethodException {
        //given
        String input = "create";

        Method method = consoleDispatcher.getClass().getDeclaredMethod("findMenu", String.class);
        method.setAccessible(true);

        //when
        try {
            Object menu = method.invoke(consoleDispatcher, input);
            //then
            assertThat(menu).isEqualTo(Menu.CREATE_VOUCHER);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
