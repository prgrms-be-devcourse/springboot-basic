package org.prgrms.kdt.domain.voucher.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.domain.voucher.model.VoucherType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"FIXED", "PERCENT"})
    @DisplayName("유효한 바우처종류를 입력할 경우 이에 해당하는 바우처타입을 반환한다.")
    void findVoucherType(String type){
        //given
        //when
        VoucherType voucherType = VoucherType.findVoucherType(type);
        //then
        assertThat(VoucherType.values()).contains(voucherType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "templates/vouchers"})
    @DisplayName("지원하지 않는 바우처 타입을 입력시 예외를 발생시킨다.")
    void exception_findVoucherType(String type){
        //given
        //when
        //then
        assertThatThrownBy(() -> VoucherType.findVoucherType(type))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 바우처타입 입니다.");
    }

}