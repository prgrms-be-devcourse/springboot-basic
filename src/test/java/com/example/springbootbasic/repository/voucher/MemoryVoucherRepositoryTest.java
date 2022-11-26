package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class MemoryVoucherRepositoryTest {

    private final LocalDateTime startAt = LocalDateTime.of(2022, Month.OCTOBER, 25, 0, 0);
    private final LocalDateTime endAt = LocalDateTime.of(2022, Month.DECEMBER, 25, 0, 0);

    private MemoryVoucherRepository voucherRepository;

    @BeforeAll
    void init() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @AfterEach
    void clear() {
        voucherRepository.deleteAllVouchers();
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}, voucherType = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("저장된 바우처 할인값, 바우처 타입 확인 성공")
    void whenSaveVoucherThenSuccessTest(long discountValue, VoucherType voucherType) {
        // given
        Voucher voucher = VoucherFactory.of(discountValue, voucherType, LocalDateTime.now(), startAt, endAt);

        // when
        Voucher savedVoucher = voucherRepository.save(voucher);

        // then
        assertThat(savedVoucher.getVoucherType(), is(voucherType));
        assertThat(savedVoucher.getDiscountValue(), is(discountValue));
    }

    @ParameterizedTest(name = "[{index}] discountValue = {0}, voucherType = {1}")
    @MethodSource("voucherDummy")
    @DisplayName("다수 바우처 저장 후 조회 성공")
    void whenFindAllVouchersThenSuccessTest(long discountValue, VoucherType voucherType) {
        // given
        final int maxCount = 100;
        Voucher voucher = VoucherFactory.of(discountValue, voucherType, LocalDateTime.now(), startAt, endAt);
        for(int count = 0; count < maxCount; count++) {
            voucherRepository.save(voucher);
        }

        // when
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();

        //then
        assertThat(allVouchers, notNullValue());
        assertThat(allVouchers, hasSize(maxCount));
    }

    @Test
    @DisplayName("비어 있는 바우처 리스트 검색시 EmptyList 반환 성공")
    void whenFindEmptyThenSuccessTest() {
        // when
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();

        // then
        assertThat(allVouchers, notNullValue());
        assertThat(allVouchers, is(Collections.emptyList()));
    }

    static Stream<Arguments> voucherDummy() {
        return Stream.of(
                Arguments.arguments(1L, FIXED_AMOUNT),
                Arguments.arguments(10L, FIXED_AMOUNT),
                Arguments.arguments(100L, FIXED_AMOUNT),
                Arguments.arguments(1000L, FIXED_AMOUNT),
                Arguments.arguments(10000L, FIXED_AMOUNT),
                Arguments.arguments(20000L, FIXED_AMOUNT),
                Arguments.arguments(30000L, FIXED_AMOUNT),
                Arguments.arguments(40000L, FIXED_AMOUNT),
                Arguments.arguments(49999L, FIXED_AMOUNT),
                Arguments.arguments(50000L, FIXED_AMOUNT),
                Arguments.arguments(1L, PERCENT_DISCOUNT),
                Arguments.arguments(10L, PERCENT_DISCOUNT),
                Arguments.arguments(20L, PERCENT_DISCOUNT),
                Arguments.arguments(30L, PERCENT_DISCOUNT),
                Arguments.arguments(40L, PERCENT_DISCOUNT),
                Arguments.arguments(99L, PERCENT_DISCOUNT),
                Arguments.arguments(100L, PERCENT_DISCOUNT)
        );
    }
}