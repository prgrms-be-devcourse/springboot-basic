package com.prgrms.devkdtorder.voucher.domain;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


class VoucherTypeTest {

    @Test
    @DisplayName("이름이나 번호로 VoucherType을 가져올 수 있어야 한다.")
    void testFindByNameOrNo() {

        //given
        String fixedAmountTypeName = "FIXEDAMOUNT";
        String fixedAmountTypeNo = "1";
        String percentDiscountTypeName = "PERCENTDISCOUNT";
        String percentDiscountTypeNo = "2";

        //when
        Optional<VoucherType> voucherType1 = VoucherType.findByNameOrNo(fixedAmountTypeName);
        Optional<VoucherType> voucherType2 = VoucherType.findByNameOrNo(fixedAmountTypeNo);
        Optional<VoucherType> voucherType3 = VoucherType.findByNameOrNo(percentDiscountTypeName);
        Optional<VoucherType> voucherType4 = VoucherType.findByNameOrNo(percentDiscountTypeNo);

        //then
        assertThat(voucherType1.isPresent()).isTrue();
        assertThat(voucherType2.isPresent()).isTrue();
        assertThat(voucherType3.isPresent()).isTrue();
        assertThat(voucherType4.isPresent()).isTrue();

        assertThat(voucherType1.get()).isEqualTo(VoucherType.FIXEDAMOUNT);
        assertThat(voucherType2.get()).isEqualTo(VoucherType.FIXEDAMOUNT);
        assertThat(voucherType3.get()).isEqualTo(VoucherType.PERCENTDISCOUNT);
        assertThat(voucherType4.get()).isEqualTo(VoucherType.PERCENTDISCOUNT);
    }

    @Test
    @DisplayName("정의되지 않은 이름이나 번호를 인자로 받으면 Optional.Empty()를 반환해야 한다.")
    void testFindByNameOrNoEmpty() {

        //given
        String value1 = "FIXEDAMOUNTG";
        String value2 = "";
        String value3 = null;
        String value4 = "3";

        //when
        Optional<VoucherType> voucherType1 = VoucherType.findByNameOrNo(value1);
        Optional<VoucherType> voucherType2 = VoucherType.findByNameOrNo(value2);
        Optional<VoucherType> voucherType3 = VoucherType.findByNameOrNo(value3);
        Optional<VoucherType> voucherType4 = VoucherType.findByNameOrNo(value4);

        //then
        assertThat(voucherType1.isEmpty()).isTrue();
        assertThat(voucherType2.isEmpty()).isTrue();
        assertThat(voucherType3.isEmpty()).isTrue();
        assertThat(voucherType4.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("정의된 VoucherType의 Name List를 순서대로 가져올 수 있어야 한다")
    void testVoucherTypeNames() {

        //when
        List<String> voucherTypeNames = VoucherType.voucherTypeNames();

        //then
        assertThat(voucherTypeNames.size()).isEqualTo(2);
        assertThat(voucherTypeNames).containsExactly(VoucherType.FIXEDAMOUNT.name(), VoucherType.PERCENTDISCOUNT.name());

        MatcherAssert.assertThat(voucherTypeNames, Matchers.hasSize(2));
        MatcherAssert.assertThat(voucherTypeNames, Matchers.contains(VoucherType.FIXEDAMOUNT.name(), VoucherType.PERCENTDISCOUNT.name()));
    }

    @Test
    @DisplayName("VoucherType별 Voucher를 생성할 수 있어야 한다")
    void testCreateVoucher() {

        //given
        VoucherType fixedAmountType = VoucherType.FIXEDAMOUNT;
        VoucherType percentDiscountType = VoucherType.PERCENTDISCOUNT;
        UUID voucherId = UUID.randomUUID();
        long amount = 1000L;
        long percent = 10L;

        //when
        Voucher voucher1 = fixedAmountType.createVoucher(voucherId, amount);
        Voucher voucher2 = percentDiscountType.createVoucher(voucherId, percent);

        //then
        assertThat(voucher1).isNotNull();
        assertThat(voucher1).isExactlyInstanceOf(FixedAmountVoucher.class);
        assertThat(voucher1.getVoucherId()).isEqualTo(voucherId);
        assertThat(voucher1.getValue()).isEqualTo(amount);

        MatcherAssert.assertThat(voucher2, Matchers.is(Matchers.notNullValue()));
        MatcherAssert.assertThat(voucher2, Matchers.is(Matchers.instanceOf(PercentDiscountVoucher.class)));
        MatcherAssert.assertThat(voucher2.getVoucherId(), Matchers.is(voucherId));
        MatcherAssert.assertThat(voucher2.getValue(), Matchers.is(percent));

    }
}