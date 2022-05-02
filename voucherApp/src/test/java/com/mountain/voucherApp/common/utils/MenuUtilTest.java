package com.mountain.voucherApp.common.utils;

import com.mountain.voucherApp.model.enums.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static com.mountain.voucherApp.common.utils.MenuUtil.getMenuMap;
import static com.mountain.voucherApp.common.utils.MenuUtil.isExit;

class MenuUtilTest {

    @Test
    @DisplayName("Enum 기준으로 올바른 Map이 만들어지는져야 한다.")
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


    @DisplayName("Exit는 0번째 Ordinal의 값을 가져야 한다.")
    @Test
    public void 타입_확인함수_테스트()
            throws Exception {
        getMenuMap();
        Assertions.assertEquals(true, isExit(0));
        Assertions.assertEquals(false, isExit(1));
        Assertions.assertEquals(false, isExit(2));
    }

}