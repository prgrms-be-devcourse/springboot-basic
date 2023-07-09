package com.prgrms.view;

import com.prgrms.controller.VoucherController;
import com.prgrms.view.command.Command;
import com.prgrms.view.command.CreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MenuTest {

    @Mock
    private Command command;
    private VoucherController voucherController;
    private ViewManager viewManager;

    @Test
    @DisplayName("소문자 list를 입력했을 때 제대로 된 메뉴 LIST를 반환하는지 확인한다.")
    public void findByMenu_LowerList_Equals() {
        //given
        String selectMenu = "list";

        //when
        Menu menu = Menu.findByMenu(selectMenu);

        //then
        assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("대문자 LIST를 입력했을 때 제대로 된 메뉴 LIST를 반환하는지 확인한다.")
    public void findByMenu_UpperList_Equals() {
        //given
        String selectMenu = "LIST";

        //when
        Menu menu = Menu.findByMenu(selectMenu);

        //then
        assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("소문자 create를 입력했을 때 제대로 된 메뉴 CREATE를 반환하는지 확인한다.")
    public void findByMenu_LowerCreate_Equals() {
        //given
        String selectMenu = "create";

        //when
        Menu menu = Menu.findByMenu(selectMenu);

        //then
        assertThat(menu).isEqualTo(Menu.CREATE);
    }

    @Test
    @DisplayName("대문자 CREATE를 입력했을 때 제대로 된 메뉴 CREATE를 반환하는지 확인한다.")
    public void findByMenu_UpperCreate_Equals() {
        //given
        String selectMenu = "CREATE";

        //when
        Menu menu = Menu.findByMenu(selectMenu);

        //then
        assertThat(menu).isEqualTo(Menu.CREATE);
    }

    @Test
    @DisplayName("소문자 exit를 입력했을 때 제대로 된 메뉴 EXIT를 반환하는지 확인한다.")
    public void findByMenu_LowerExit_Equals() {
        //given
        String selectMenu = "exit";

        //when
        Menu menu = Menu.findByMenu(selectMenu);

        //then
        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("대문자 EXIT를 입력했을 때 제대로 된 메뉴 EXIT를 반환하는지 확인한다.")
    public void findByMenu_UpperExit_Equals() {
        //given
        String selectMenu = "EXIT";

        //when
        Menu menu = Menu.findByMenu(selectMenu);

        //then
        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("대소문자 섞인 메뉴를 입력했을 때 제대로 된 대문자 메뉴를 반환하는지 확인한다.")
    public void findByMenu_MixedMenu_Equals() {
        //given
        String selectMenu = "ExIt";

        //when
        Menu menu = Menu.findByMenu(selectMenu);

        //then
        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("없는 메뉴에 대한 예외를 반환한다.")
    public void findByMenu_NotExistMenu_ThrowsException() {
        //given
        String menu = "cccc";

        //when_then
        assertThatThrownBy(() -> Menu.findByMenu(menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 선택지가 아닙니다.");
    }

    @Test
    @DisplayName("create 메뉴가 CreateCommand를 반환하는지 확인한다.")
    void createCommand_CreateManu_CorrectCommand() {
        //given
        Menu menu = Menu.CREATE;

        //when
        Command command = menu.createCommand(voucherController, viewManager);

        //then
        assertThat(command).isInstanceOf(CreateCommand.class);
    }
}
