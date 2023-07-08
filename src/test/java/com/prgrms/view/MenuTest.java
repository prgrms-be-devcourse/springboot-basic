package com.prgrms.view;

import com.prgrms.view.command.Command;
import com.prgrms.view.command.CommandFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//assertJ
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


class MenuTest {

    @Mock
    private CommandFactory commandFactory;

    @Test
    @DisplayName("소문자 list")
    public void FindByMenu_LowerList_Equals() {
        String selectMenu = "list";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("대문자 LIST")
    public void FindByMenu_UpperList_Equals() {
        String selectMenu = "LIST";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("소문자 create")
    public void FindByMenu_LowerCreate_Equals() {
        String selectMenu = "create";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.CREATE);
    }

    @Test
    @DisplayName("대문자 CREATE")
    public void FindByMenu_UpperCreate_Equals() {
        String selectMenu = "CREATE";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.CREATE);
    }

    @Test
    @DisplayName("소문자 exit")
    public void FindByMenu_LowerExit_Equals() {
        String selectMenu = "exit";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("대문자 EXIT")
    public void FindByMenu_UpperExit_Equals() {
        String selectMenu = "EXIT";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("대소문자 섞인 메뉴")
    public void FindByMenu_MixedMenu_Equals() {
        String selectMenu = "ExIt";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("없는 메뉴에 대한 예외 테스트")
    public void findByMenu_NotExistMenu_ThrowsException() {
        String menu = "cccc";

        assertThatThrownBy(() -> Menu.findByMenu(menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 선택지가 아닙니다.");
    }

    @Test
    void createCommand_CorrectCommandInstanceBasedOnMenu() {
        //given
        MockitoAnnotations.openMocks(this);
        Menu menu = Menu.CREATE;
        Command mockCommand = mock(Command.class);
        when(commandFactory.createCreateCommand()).thenReturn(mockCommand);

        //when
        Command result = menu.createCommand(commandFactory);

        // then
        switch (menu) {
            case EXIT:
                verify(commandFactory, times(1)).createExitCommand();
                break;
            case CREATE:
                verify(commandFactory, times(1)).createCreateCommand();
                break;
            case LIST:
                verify(commandFactory, times(1)).createListCommand();
                break;
        }

        assertThat(result).isEqualTo(mockCommand);
    }
}
