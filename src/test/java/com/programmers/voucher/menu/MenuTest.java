package com.programmers.voucher.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.programmers.voucher.menu.Menu.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuTest {

    @Test
    @DisplayName("메뉴에 없는 항목을 입력한 경우 런타임 에러가 발생해야 한다.")
    void 메뉴_조회_테스트() {
        assertThrows(RuntimeException.class, () -> findMenu("hello"));
    }


    @Test
    @DisplayName("메뉴에 존재하는 EXIT을 입력한 경우 헤당 메뉴를 리턴해야 한다.")
    void 메뉴_EXIT_조회테스트() {
        Menu menu = findMenu(EXIT.getMenu());
        assertEquals(EXIT, menu);
    }

    @Test
    @DisplayName("메뉴에 존재하는 CREATE를 입력한 경우 헤당 메뉴를 리턴해야 한다.")
    void 메뉴_CREATE_조회테스트() {
        Menu menu = findMenu(CREATE.getMenu());
        assertEquals(CREATE, menu);
    }

    @Test
    @DisplayName("메뉴에 존재하는 LIST를 입력한 경우 헤당 메뉴를 리턴해야 한다.")
    void 메뉴에서LIST조회테스트() {
        Menu menu = findMenu(LIST.getMenu());
        assertEquals(LIST, menu);
    }
}