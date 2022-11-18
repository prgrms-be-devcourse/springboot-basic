package com.prgrms.springbootbasic.voucher.factory;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;
import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
class VoucherFactoryTest {

    private final Map<VoucherType, VoucherFactory> voucherFactoryMap = new EnumMap<>(VoucherType.class);

    @BeforeAll
    public void init() {
        List<VoucherFactory> voucherFactories = List.of(
                new FixedAmountVoucherFactory(),
                new PercentVoucherFactory());
        voucherFactories.forEach(factory -> this.voucherFactoryMap.put(factory.getType(), factory));
    }

    @Test
    @DisplayName("FixedAmountVoucher를 생성할 수 있다")
    void createFixed() {
        //given
        VoucherFactory voucherFactory = voucherFactoryMap.get(VoucherType.FIXED_AMOUNT);

        //when
        Voucher voucher = voucherFactory.createVoucher(10);

        //then
        assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.FIXED_AMOUNT);
    }

    @Test
    @DisplayName("입력 형식이 잘못된 discountAmount가 주어질 경우 생성에 실패한다")
    void createFixedWithWrongNumberFormat() {
        //given
        VoucherFactory voucherFactory = voucherFactoryMap.get(VoucherType.FIXED_AMOUNT);

        //when&then
        assertThrows(NumberFormatException.class, () -> voucherFactory.requestVoucher("wrong"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "10001"})
    @DisplayName("FixedAmountVoucher 생성 시 잘못된 범위의 discountAmount가 주어질 경우 생성에 실패한다")
    void createFixedWithOutOfBound(String discountAmount) {
        //given
        VoucherFactory voucherFactory = voucherFactoryMap.get(VoucherType.FIXED_AMOUNT);

        //when&then
        assertThrows(AmountOutOfBoundException.class, () -> voucherFactory.requestVoucher(discountAmount));
    }

    @Test
    @DisplayName("PercentVoucher를 생성할 수 있다")
    void name() {
        //given
        VoucherFactory voucherFactory = voucherFactoryMap.get(VoucherType.PERCENT);

        //when
        Voucher voucher = voucherFactory.createVoucher(10);

        //then
        assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.PERCENT);

    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "100"})
    @DisplayName("PercentVoucher 생성 시 잘못된 범위의 discountAmount가 주어질 경우 생성에 실패한다")
    void createPercent(String discountAmount) {
        //given
        VoucherFactory voucherFactory = voucherFactoryMap.get(VoucherType.PERCENT);

        //when&then
        assertThrows(AmountOutOfBoundException.class, () -> voucherFactory.requestVoucher(discountAmount));
    }
}