package org.programmers.VoucherManagement.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTypeTest {
    @Test
    @DisplayName("입력값에 따라 CommandType을 반환하는 테스트 - 성공")
    void 입력값에따라_Command를_반환_성공() {
        //given
        int listInput = 8;
        int createInput = 7;

        //when
        MenuType listTypeExpect = MenuType.from(listInput);
        MenuType createTypeExpect = MenuType.from(createInput);

        //then
        assertThat(listTypeExpect).isEqualTo(MenuType.LIST);
        assertThat(createTypeExpect).isEqualTo(MenuType.CREATE);
    }

    @Test
    @DisplayName("입력값에 따라 CommandType을 반환하는 테스트 - 실패")
    void 입력값에따라_Command를_반환_실패() {
        int input = 43;

        assertThatThrownBy(() -> MenuType.from(input))
                .isInstanceOf(VoucherException.class)
                .hasMessage("해당하는 Command가 존재하지 않습니다.");
    }

}
