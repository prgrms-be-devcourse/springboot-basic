package com.prgrms.io;

import com.prgrms.model.voucher.VoucherPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    @DisplayName("소문자 list")
    public void findLowerList() {
        String selectMenu = "list";
        Optional<Menu> menu = Menu.findByMenu(selectMenu);

        assertEquals(Menu.LIST, menu.get());
    }

    @Test
    @DisplayName("대문자 LIST")
    public void findUpperList() {
        String selectMenu = "LIST";
        Optional<Menu> menu = Menu.findByMenu(selectMenu);

        assertEquals(Menu.LIST, menu.get());
    }

    @Test
    @DisplayName("소문자 create")
    public void findLowerCreate() {
        String selectMenu = "create";
        Optional<Menu> menu = Menu.findByMenu(selectMenu);

        assertEquals(Menu.CREATE, menu.get());
    }

    @Test
    @DisplayName("대문자 CREATE")
    public void findUpperCREATE() {
        String selectMenu = "CREATE";
        Optional<Menu> menu = Menu.findByMenu(selectMenu);

        assertEquals(Menu.CREATE, menu.get());
    }

    @Test
    @DisplayName("소문자 exit")
    public void findLowerExit() {
        String selectMenu = "exit";
        Optional<Menu> menu = Menu.findByMenu(selectMenu);

        assertEquals(Menu.EXIT, menu.get());
    }

    @Test
    @DisplayName("대문자 EXIT")
    public void findUpperExit() {
        String selectMenu = "EXIT";
        Optional<Menu> menu = Menu.findByMenu(selectMenu);

        assertEquals(Menu.EXIT, menu.get());
    }

    @Test
    @DisplayName("대소문자 섞인 메뉴")
    public void findMixedMenu() {
        String selectMenu = "ExIt";
        Optional<Menu> menu = Menu.findByMenu(selectMenu);

        assertEquals(Menu.EXIT, menu.get());
    }

    @Test
    @DisplayName("없는 메뉴에 대한 예외 테스트")
    void findByPolicyNotExistVoucherPolicy() {
        String menu = "cccc";
        Optional<Menu> selectedMenu = Menu.findByMenu(menu);

        assertEquals(Optional.empty(), selectedMenu);
    }
}