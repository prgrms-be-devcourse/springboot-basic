package com.programmers.voucher.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.programmers.menu.Menu.findMenu;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuTest {

    @Test
    @DisplayName("메뉴에 없는 항목을 입력한 경우 런타임 에러가 발생해야 한다.")
    void 메뉴_조회_테스트() {
        assertThrows(RuntimeException.class, () -> findMenu("hello"));
    }
}