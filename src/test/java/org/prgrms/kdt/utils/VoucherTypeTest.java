package org.prgrms.kdt.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.exceptions.InvalidITypeInputException;

import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    @Test
    @DisplayName("콘솔에서 fix를 입력받으면 고정금액 할인 바우처 타입을 반환한다.")
    void testSelectFixedAmountVoucherType(){
        // given
        String input = "fix";
        // when
        VoucherType fixed = VoucherType.findVoucherTypeByInput(input);
        // then
        assertEquals(VoucherType.FIXED_VOUCHER, fixed);
    }

    @Test
    @DisplayName("콘솔에서 percent를 입력받으면 퍼센트 할인 바우처 타입으로 반환한다.")
    void testSelectPercentDiscountVoucherType(){
        // given
        String input = "percent";
        // when
        VoucherType percent = VoucherType.findVoucherTypeByInput(input);
        // then
        assertEquals(VoucherType.PERCENT_VOUCHER, percent);
    }

    @Test
    @DisplayName("클래스명을 전달하면 그 클래스 타입의 바우처 타입을 반환한다.")
    void testSelectVoucherTypeByClassName(){
        // given
        String fixed = "FixedAmountVoucher";
        String percent = "PercentDiscountVoucher";
        // when
        VoucherType fixedType = VoucherType.findVoucherTypeByClassName(fixed);
        VoucherType percentType = VoucherType.findVoucherTypeByClassName(percent);
        // then
        assertEquals(VoucherType.FIXED_VOUCHER, fixedType);
        assertEquals(VoucherType.PERCENT_VOUCHER, percentType);
    }

    @DisplayName("해당되지 않는 값을 입력할 경우 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"123", "fic", "p", "percnet", "exit", "0"})
    void testInvalidSelectInput(String input){
        assertThrows(InvalidITypeInputException.class, () -> {
            VoucherType.findVoucherTypeByInput(input);
        });
    }

    @DisplayName("해당되지 않는 클래스명을 입력할 경우 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"MemberShipDiscountVoucher", "FixVoucher", "PercentVoucher"})
    void testInvalidClassName(String className){
        assertThrows(InvalidITypeInputException.class, () -> {
            VoucherType.findVoucherTypeByClassName(className);
        });
    }
}