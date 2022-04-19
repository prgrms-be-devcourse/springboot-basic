package com.mountain.voucherApp.utils;

import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.enums.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Map;

import static com.mountain.voucherApp.utils.MenuUtil.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuUtilTest {

    @Test
    public void Menu_Map_조회_테스트() throws Exception {
        //given
        Map<String, Menu> menuMap = getMenuMap();
        //when
        Arrays.stream(Menu.values())
                .forEach((menu) -> {
                    Menu getMenu = menuMap.get(menu.getValue());
                    //then
                    Assertions.assertEquals(menu, getMenu);
                });
    }

    @ParameterizedTest
    @CsvSource({ // given
            "exit, true, false, false",
            "create, false, true, false",
            "list, false, false, true"
    })
    public void 타입_확인함수_테스트(String command, boolean expected1, boolean expected2, boolean expected3)
            throws Exception {
        Assertions.assertEquals(expected1, isExit(command));
        Assertions.assertEquals(expected2, isCreate(command));
        Assertions.assertEquals(expected3, isList(command));
    }

}