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
    public void FindByMenu_LowerList_Equals() {
        String selectMenu = "list";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("대문자 LIST를 입력했을 때 제대로 된 메뉴 LIST를 반환하는지 확인한다.")
    public void FindByMenu_UpperList_Equals() {
        String selectMenu = "LIST";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("소문자 create를 입력했을 때 제대로 된 메뉴 CREATE를 반환하는지 확인한다.")
    public void FindByMenu_LowerCreate_Equals() {
        String selectMenu = "create";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.CREATE);
    }

    @Test
    @DisplayName("대문자 CREATE를 입력했을 때 제대로 된 메뉴 CREATE를 반환하는지 확인한다.")
    public void FindByMenu_UpperCreate_Equals() {
        String selectMenu = "CREATE";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.CREATE);
    }

    @Test
    @DisplayName("소문자 exit를 입력했을 때 제대로 된 메뉴 EXIT를 반환하는지 확인한다.")
    public void FindByMenu_LowerExit_Equals() {
        String selectMenu = "exit";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("대문자 EXIT를 입력했을 때 제대로 된 메뉴 EXIT를 반환하는지 확인한다.")
    public void FindByMenu_UpperExit_Equals() {
        String selectMenu = "EXIT";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("대소문자 섞인 메뉴를 입력했을 때 제대로 된 대문자 메뉴를 반환하는지 확인한다.")
    public void FindByMenu_MixedMenu_Equals() {
        String selectMenu = "ExIt";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("없는 메뉴에 대한 예외를 반환한다.")
    public void findByMenu_NotExistMenu_ThrowsException() {
        String menu = "cccc";

        assertThatThrownBy(() -> Menu.findByMenu(menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 선택지가 아닙니다.");
    }

    @Test
    @DisplayName("create 메뉴가 CreateCommand를 반환하는지 확인한다.")
    void createCommand_CorrectCommandInstanceBasedOnMenu() {
        //given
        Menu menu = Menu.CREATE;

        //when
        Command command = menu.createCommand(voucherController, viewManager);

        //then
        assertThat(command).isInstanceOf(CreateCommand.class);
    }

}
