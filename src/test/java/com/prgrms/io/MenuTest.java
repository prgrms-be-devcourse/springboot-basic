package com.prgrms.io;

//Junit

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//assertJ
import static org.assertj.core.api.Assertions.*;


class MenuTest {

    @Test
    @DisplayName("소문자 list")
    public void findLowerList() {
        String selectMenu = "list";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("대문자 LIST")
    public void findUpperList() {
        String selectMenu = "LIST";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.LIST);
    }

    @Test
    @DisplayName("소문자 create")
    public void findLowerCreate() {
        String selectMenu = "create";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.CREATE);
    }

    @Test
    @DisplayName("대문자 CREATE")
    public void findUpperCREATE() {
        String selectMenu = "CREATE";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.CREATE);
    }

    @Test
    @DisplayName("소문자 exit")
    public void findLowerExit() {
        String selectMenu = "exit";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("대문자 EXIT")
    public void findUpperExit() {
        String selectMenu = "EXIT";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("대소문자 섞인 메뉴")
    public void findMixedMenu() {
        String selectMenu = "ExIt";
        Menu menu = Menu.findByMenu(selectMenu);

        assertThat(menu).isEqualTo(Menu.EXIT);
    }

    @Test
    @DisplayName("없는 메뉴에 대한 예외 테스트")
    void findByPolicyNotExistVoucherPolicy() {
        String menu = "cccc";

        assertThatThrownBy(() -> Menu.findByMenu(menu))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 선택지가 아닙니다.");
    }

}
