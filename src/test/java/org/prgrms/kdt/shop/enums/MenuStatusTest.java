package org.prgrms.kdt.shop.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class MenuStatusTest {
    @Test
    @DisplayName("메뉴 상태 테스트")
    void find( ) {
        assertThat(MenuStatus.find("list"), is(MenuStatus.LIST));
        assertThat(MenuStatus.find("create"), is(MenuStatus.CREATE));
        assertThat(MenuStatus.find("exit"), is(MenuStatus.EXIT));
        try {
            MenuStatus.find("이상한문자");
        } catch (Exception e) {
        }
        ;
    }

}