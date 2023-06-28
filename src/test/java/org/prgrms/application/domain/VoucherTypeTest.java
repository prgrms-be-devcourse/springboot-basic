package org.prgrms.application.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class VoucherTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"fixed","FiXed"})
    @DisplayName("fixed voucher type 을 찾아낸다.")
    void findFixedVoucherType(String voucherType){
        VoucherType foundType = VoucherType.findBySelection(voucherType);
        assertThat(foundType).isEqualTo(VoucherType.FIXED);
    }


    @ParameterizedTest
    @ValueSource(strings = {"Percent","percent"})
    @DisplayName("fixed voucher type 을 찾아낸다.")
    void findPercentVoucherType(String voucherType){
        VoucherType foundType = VoucherType.findBySelection(voucherType);
        assertThat(foundType).isEqualTo(VoucherType.PERCENT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"dsfaf","sdf"})
    @DisplayName("실페 케이스 : voucherType가 지정하지 않은 값 입력")
    void failWrongVoucherType(String voucherType){
        assertThatThrownBy(() -> VoucherType.findBySelection(voucherType))
                .isInstanceOf(IllegalArgumentException.class);
    }

}