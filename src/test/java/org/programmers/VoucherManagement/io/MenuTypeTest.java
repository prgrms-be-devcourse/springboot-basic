package org.programmers.VoucherManagement.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
public class MenuTypeTest {
    @Test
    @DisplayName("입력값에 따라 MenuType을 반환한다. - 성공")
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

    @ParameterizedTest
    @DisplayName("유효하지 않은 값이 들어오면 MenuType을 반환할 수 없다. - 실패")
    @CsvSource({"-1, 0, 499"})
    void from_InputMenu_ThrowVoucherException(int inValidMenuNumber) {
        assertThatThrownBy(() -> MenuType.from(inValidMenuNumber))
                .isInstanceOf(VoucherException.class)
                .hasMessage("해당하는 Command가 존재하지 않습니다.");
    }

    @ParameterizedTest
    @DisplayName("MenuType의 모든 값을 테스트한다. - 성공")
    @EnumSource(MenuType.class)
    void test_MenuTypeNotNull_Success(MenuType menuType) {
        Assertions.assertThat(menuType).isNotNull();
    }

}
