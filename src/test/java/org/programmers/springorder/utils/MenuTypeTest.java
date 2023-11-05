package org.programmers.springorder.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.assertThat;

class MenuTypeTest {

    @Test
    @DisplayName("올바른 input menu를 넣을 시에 선택한 menu를 반환한다.")
    public void inputMenuCorrectTest(){

        String input1 = "1";
        String input2 = "2";
        String input3 = "3";
        String input4 = "4";
        String input5 = "5";
        String input6 = "6";
        String input7 = "7";
        String input8 = "8";

        MenuType menuType1 = MenuType.selectMenu(input1);
        MenuType menuType2 = MenuType.selectMenu(input2);
        MenuType menuType3 = MenuType.selectMenu(input3);
        MenuType menuType4 = MenuType.selectMenu(input4);
        MenuType menuType5 = MenuType.selectMenu(input5);
        MenuType menuType6 = MenuType.selectMenu(input6);
        MenuType menuType7 = MenuType.selectMenu(input7);
        MenuType menuType8 = MenuType.selectMenu(input8);

        assertThat(menuType1).isEqualTo(MenuType.EXIT);
        assertThat(menuType2).isEqualTo(MenuType.CREATE);
        assertThat(menuType3).isEqualTo(MenuType.LIST);
        assertThat(menuType4).isEqualTo(MenuType.BLACK);
        assertThat(menuType5).isEqualTo(MenuType.ALLOCATE);
        assertThat(menuType6).isEqualTo(MenuType.GET_OWNER_VOUCHER);
        assertThat(menuType7).isEqualTo(MenuType.DELETE_VOUCHER);
        assertThat(menuType8).isEqualTo(MenuType.SEARCH_VOUCHER_OWNER);
    }

    @Test
    @DisplayName("올바르지 않은 input menu를 넣을 시에 선택한 InputMismatchException을 반환한다..")
    public void WrongInputMenuCorrectTest(){
        String wrongInput1 = "일번";
        String wrongInput2 = "13";
        String wrongInput3 = "선택안함";

        Assertions.assertThatThrownBy(() ->MenuType.selectMenu(wrongInput1)).isInstanceOf(InputMismatchException.class);
        Assertions.assertThatThrownBy(() ->MenuType.selectMenu(wrongInput2)).isInstanceOf(InputMismatchException.class);
        Assertions.assertThatThrownBy(() ->MenuType.selectMenu(wrongInput3)).isInstanceOf(InputMismatchException.class);
    }

}