package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.domain.voucher.FixedAmountVoucher;
import com.devcourse.springbootbasic.application.domain.voucher.PercentDiscountVoucher;
import com.devcourse.springbootbasic.application.domain.voucher.Voucher;
import com.devcourse.springbootbasic.application.dto.DiscountValue;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository voucherRepository;

    @BeforeAll
    void init() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Order(1)
    @ParameterizedTest
    @DisplayName("바우처 생성 시 바우처맵에 추가되면 성공")
    @MethodSource("provideVouchers")
    void insert(Voucher voucher) {
        var result = voucherRepository.insert(voucher);
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Voucher.class));
    }

    @Order(2)
    @Test
    @DisplayName("바우처 리스트 반환 시 성공")
    void findAll() {
        var result = voucherRepository.findAll();
        assertThat(result, notNullValue());
        assertThat(result.size(), is(greaterThan(0)));
    }

    static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(new FixedAmountVoucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, new DiscountValue(VoucherType.FIXED_AMOUNT, "100"))),
                Arguments.of(new PercentDiscountVoucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, new DiscountValue(VoucherType.PERCENT_DISCOUNT, "2")))
        );
    }

}