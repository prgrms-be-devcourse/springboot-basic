package org.prgrms.kdt.domain.customer.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.domain.voucher.model.VoucherType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTypeTest {


    @ParameterizedTest
    @ValueSource(strings = {"NORMAL", "BLACK"})
    @DisplayName("유효한 고객 타입을 입력할 경우 이에 해당하는 고객타입을 반환한다.")
    public void findCustomerType(String type){
        //given
        //when
        CustomerType customerType = CustomerType.findCustomerType(type);
        //then
        assertThat(CustomerType.values()).contains(customerType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "BLACKLIST", "NOT"})
    @DisplayName("지원하지 않는 고객 타입을 입력시 예외를 발생시킨다.")
    public void exception_findCustomerType(String type){
        //given
        //when
        //then
        assertThatThrownBy(() -> CustomerType.findCustomerType(type))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 고객타입 입니다.");
    }

}