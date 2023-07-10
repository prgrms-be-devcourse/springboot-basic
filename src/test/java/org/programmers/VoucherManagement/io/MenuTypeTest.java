package org.programmers.VoucherManagement.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTypeTest {
    @Test
    @DisplayName("입력값에 따라 MenuType을 반환하는 테스트 - 성공")
    void from_InputMenu_EqualsMenuType() {
        //given
        int listInput = 9;
        int createInput = 6;

        //when
        MenuType listTypeExpect = MenuType.from(listInput);
        MenuType createTypeExpect = MenuType.from(createInput);

        //then
        assertThat(listTypeExpect).isEqualTo(MenuType.VOUCHER_LIST);
        assertThat(createTypeExpect).isEqualTo(MenuType.INSERT_VOUCHER);
    }

    @Test
    @DisplayName("입력값에 따라 MenuType을 반환하는 테스트 - 실패")
    void from_InputMenu_ThrowVoucherException() {
        int input = 43;

        assertThatThrownBy(() -> MenuType.from(input))
                .isInstanceOf(VoucherException.class)
                .hasMessage("해당하는 Command가 존재하지 않습니다.");
    }

}
