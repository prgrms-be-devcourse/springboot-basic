package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.vo.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.vo.VoucherType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("dev")
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
    @DisplayName("바우처 생성 시 바우처맵에 추가되면 성공한다.")
    @MethodSource("provideVouchers")
    void insert_ParamVoucher_InsertAndReturnVoucher(Voucher voucher) {
        var result = voucherRepository.insert(voucher);
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Voucher.class));
    }

    @Order(2)
    @Test
    @DisplayName("바우처 리스트 반환 시 성공한다.")
    void findAllVouchers_ParamVoucher_ReturnVoucherList() {
        var result = voucherRepository.findAllVouchers();
        assertThat(result, notNullValue());
        assertThat(result.size(), is(greaterThan(0)));
    }

    static Stream<Arguments> provideVouchers() {
        return Stream.of(
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, DiscountValue.from(VoucherType.FIXED_AMOUNT, "100"))),
                Arguments.of(new Voucher(UUID.randomUUID(), VoucherType.PERCENT_DISCOUNT, DiscountValue.from(VoucherType.PERCENT_DISCOUNT, "2")))
        );
    }

}