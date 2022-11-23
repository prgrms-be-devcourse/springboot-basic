package com.example.springbootbasic.service.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("csv")
class VoucherServiceTest {

    @Autowired
    private VoucherService voucherService;

    @AfterEach
    void clearData() {
        voucherService.deleteAllVouchers();
    }

    @ParameterizedTest(name = "[{index}] discountValues = {0}, voucherTypes = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("여러 타입의 바우처 저장 후 모든 바우처 검색을 성공한다.")
    void whenFindAllVouchersThenSuccessTest(List<Long> discountValues, List<VoucherType> voucherTypes) {
        // given
        int voucherSize = discountValues.size();
        for (int currVoucherIndex = 0; currVoucherIndex < voucherSize; currVoucherIndex++) {
            Voucher voucher = VoucherFactory.of(discountValues.get(currVoucherIndex), voucherTypes.get(currVoucherIndex));
            voucherService.saveVoucher(voucher);
        }

        // when
        List<Voucher> allVouchers = voucherService.findAllVouchers();

        //then
        assertThat(allVouchers.size()).isEqualTo(voucherSize);
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}, voucherType = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("여러 타입의 바우처 저장 후 바우처 타입별 검색을 성공한다.")
    void whenFindAllVouchersByTypeThenSuccessTest(List<Long> discountValues, List<VoucherType> voucherTypes) {
        // given
        int voucherSize = discountValues.size();
        for (int currVoucherIndex = 0; currVoucherIndex < voucherSize; currVoucherIndex++) {
            Voucher voucher = VoucherFactory.of(discountValues.get(currVoucherIndex), voucherTypes.get(currVoucherIndex));
            voucherService.saveVoucher(voucher);
        }

        // when
        int inputFixedVoucherCount = (int) voucherTypes.stream()
                .filter(voucherType -> voucherType == FIXED_AMOUNT)
                .count();
        int inputPercentVoucherCount = (int) voucherTypes.stream()
                .filter(voucherType -> voucherType == PERCENT_DISCOUNT)
                .count();

        List<Voucher> allVouchers = voucherService.findAllVouchers();
        System.out.println("allVouchers = " + allVouchers);
        int fixedVoucherCount = (int) allVouchers.stream()
                .filter(voucher -> voucher.getVoucherType() == FIXED_AMOUNT)
                .count();
        int percentVoucherCount = (int) allVouchers.stream()
                .filter(voucher -> voucher.getVoucherType() == PERCENT_DISCOUNT)
                .count();

        //then
        assertThat(fixedVoucherCount).isEqualTo(inputFixedVoucherCount);
        assertThat(percentVoucherCount).isEqualTo(inputPercentVoucherCount);
    }

    @Test
    @DisplayName("바우처 타입을 통해서 모든 바우처 검색에 성공한다.")
    void whenFindAllVoucherByVoucherTypeThenSuccessTest() {
        // given
        Voucher voucher1 = VoucherFactory.of(10L, FIXED_AMOUNT);
        Voucher voucher2 = VoucherFactory.of(1000L, FIXED_AMOUNT);
        Voucher voucher3 = VoucherFactory.of(40000L, FIXED_AMOUNT);

        // when
        voucherService.saveVoucher(voucher1);
        voucherService.saveVoucher(voucher2);
        voucherService.saveVoucher(voucher3);
        List<Voucher> vouchers = voucherService.findAllVoucherByVoucherType(FIXED_AMOUNT);

        // then
        assertThat(vouchers).allMatch(voucher -> voucher.getVoucherType() == FIXED_AMOUNT);
        assertThat(vouchers).hasSize(3);
    }

    static Stream<Arguments> voucherDummy() {
        return Stream.of(
                Arguments.arguments(
                        List.of(1L, 10L, 100L, 1000L, 10000L, 20000L),
                        List.of(FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT)
                ),
                Arguments.arguments(
                        List.of(30000L, 40000L, 49999L, 50L, 1L, 10L),
                        List.of(FIXED_AMOUNT, FIXED_AMOUNT, FIXED_AMOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT)
                ),
                Arguments.arguments(
                        List.of(10L, 20L, 30L, 40L, 99L, 100L),
                        List.of(PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT, PERCENT_DISCOUNT)
                )
        );
    }
}