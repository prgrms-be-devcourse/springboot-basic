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
        Map<Integer, Menu> menuMap = getMenuMap();
        //when
        Arrays.stream(Menu.values())
                .forEach((menu) -> {
                    Menu getMenu = menuMap.get(menu.ordinal());
                    //then
                    Assertions.assertEquals(menu, getMenu);
                });
    }

    @ParameterizedTest
    @CsvSource({ // given
            "0, true",
            "1, false",
            "2, false"
    })
    public void 타입_확인함수_테스트(int ordinal, boolean expected1)
            throws Exception {
        getMenuMap();
        Assertions.assertEquals(expected1, isExit(ordinal));
    }

}